package com.dragn0007.dragnlivestock.entities.cow;

import com.dragn0007.dragnlivestock.LivestockOverhaul;
import com.dragn0007.dragnlivestock.common.gui.OxMenu;
import com.dragn0007.dragnlivestock.entities.EntityTypes;
import com.dragn0007.dragnlivestock.entities.ai.BullAroundLikeCrazyGoal;
import com.dragn0007.dragnlivestock.entities.ai.CattleFollowHerdLeaderGoal;
import com.dragn0007.dragnlivestock.entities.ai.OAvoidEntityGoal;
import com.dragn0007.dragnlivestock.entities.ai.PauseMeleeAttackGoal;
import com.dragn0007.dragnlivestock.entities.util.AbstractOMount;
import com.dragn0007.dragnlivestock.entities.util.LOAnimations;
import com.dragn0007.dragnlivestock.entities.util.Taggable;
import com.dragn0007.dragnlivestock.entities.util.marking_layer.BovineMarkingOverlay;
import com.dragn0007.dragnlivestock.items.LOItems;
import com.dragn0007.dragnlivestock.items.custom.BrandTagItem;
import com.dragn0007.dragnlivestock.util.LOTags;
import com.dragn0007.dragnlivestock.util.LivestockOverhaulClientConfig;
import com.dragn0007.dragnlivestock.util.LivestockOverhaulCommonConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import net.neoforged.fml.ModList;
import net.minecraft.core.registries.Registries;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animation.Animation;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

public class OCow extends AbstractOMount implements GeoEntity, Taggable {

	public OCow leader;
	public int herdSize = 1;

	public OCow(EntityType<? extends OCow> type, Level level) {
		super(type, level);
		setMilked(false);
	}

	protected static final ResourceKey<LootTable> LOOT_TABLE = ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "entities/o_cow"));
	protected static final ResourceKey<LootTable> VANILLA_LOOT_TABLE = ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.fromNamespaceAndPath("minecraft", "entities/cow"));
	protected static final ResourceKey<LootTable> TFC_LOOT_TABLE = ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.fromNamespaceAndPath("tfc", "entities/cow"));
	@Override
	public @NotNull ResourceKey<LootTable> getDefaultLootTable() {
		if (LivestockOverhaulCommonConfig.USE_VANILLA_LOOT.get()) {
			return VANILLA_LOOT_TABLE;
		} else if (ModList.get().isLoaded("tfc")) {
			return TFC_LOOT_TABLE;
		} else {
			return LOOT_TABLE;
		}
	}

	public boolean isMeatBreed() {
		return this.getBreed() == 0 || this.getBreed() == 2 || this.getBreed() == 4 || this.getBreed() == 8 || this.getBreed() == 10 || this.getBreed() == 11;
	}

	public boolean isNormalBreed() {
		return this.getBreed() == 1 || this.getBreed() == 5;
	}

	public boolean isMiniBreed() {
		return this.getBreed() == 3;
	}

	public boolean isDairyBreed() {
		return this.getBreed() == 6 || this.getBreed() == 7;
	}

	@Override
	public Vec3 getLeashOffset() {
		return new Vec3(0D, (double)this.getEyeHeight() * 1.0F, (double)(this.getBbWidth() * 0.9F));
		//              ^ Side offset                      ^ Height offset                   ^ Length offset
	}

	public static AttributeSupplier.Builder createAttributes() {
		return Mob.createMobAttributes()
				.add(Attributes.MAX_HEALTH, 18.0D)
				.add(Attributes.ATTACK_DAMAGE, 1.5D)
				.add(Attributes.MOVEMENT_SPEED, 0.17F);
	}

	public static final Ingredient FOOD_ITEMS = Ingredient.of(LOTags.Items.O_COW_EATS);
	public boolean isFood(ItemStack stack) {
		return FOOD_ITEMS.test(stack);
	}

	@Override
	public void playEmote(String emoteName, String loopType) {}
	@Override
	public boolean canWearArmor() {
		return false;
	}
	@Override
	public boolean canPerformRearing() {
		return false;
	}

	public void registerGoals() {
		this.goalSelector.addGoal(0, new FloatGoal(this));
		this.goalSelector.addGoal(1, new HurtByTargetGoal(this));
		this.goalSelector.addGoal(1, new PauseMeleeAttackGoal(this, 2.3D, true));
		this.goalSelector.addGoal(1, new CowPanicGoal(2.3D));
		this.goalSelector.addGoal(2, new BreedGoal(this, 1.0D));
		this.goalSelector.addGoal(3, new TemptGoal(this, 1.25D, Ingredient.of(Items.WHEAT), false));
		this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.25D));
		this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0D));
		this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 6.0F));
		this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
		this.goalSelector.addGoal(3, new CattleFollowHerdLeaderGoal(this, 16.0F));
		this.goalSelector.addGoal(2, new BullAroundLikeCrazyGoal(this, 1.7F));

		this.goalSelector.addGoal(1, new OAvoidEntityGoal<>(this, LivingEntity.class, 15.0F, 2.0F, 2.3D, livingEntity ->
				livingEntity.getType().is(LOTags.Entity_Types.HORSES) && (livingEntity instanceof AbstractHorse && livingEntity.isVehicle()) && !this.isLeashed() && LivestockOverhaulCommonConfig.HORSE_HERD_ANIMALS.get())
		);

		this.goalSelector.addGoal(1, new OAvoidEntityGoal<>(this, LivingEntity.class, 15.0F, 2.0F, 2.3D, livingEntity ->
				livingEntity.getType().is(LOTags.Entity_Types.WOLVES) && (livingEntity instanceof TamableAnimal && !((TamableAnimal) livingEntity).isTame() && !this.isLeashed())
		));

		this.goalSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, 10, true, false, entity ->
				entity instanceof Player && this.getBreed() == 11 && !this.isBaby() && this.isMale() && entity.getMainHandItem().is(ItemTags.SWORDS)
		));
	}

	public float getStepHeight() {
		return 1F;
	}

	protected final AnimatableInstanceCache geoCache = GeckoLibUtil.createInstanceCache(this);

	protected <T extends GeoAnimatable> PlayState predicate(software.bernie.geckolib.animation.AnimationState<T> tAnimationState) {
		double x = this.getX() - this.xo;
		double z = this.getZ() - this.zo;
		boolean isMoving = (x * x + z * z) > 0.0001;
		double currentSpeed = this.getDeltaMovement().lengthSqr();
		double speedThreshold = 0.02;

		AnimationController<T> controller = tAnimationState.getController();

		if (this.isHarnessed() && this.isVehicle()) {
			controller.setAnimation(RawAnimation.begin().then("buck", Animation.LoopType.LOOP));
			controller.setAnimationSpeed(1.3);
		} else {
			if (isMoving) {
				// o_cow run/charge clips contain mismatched horse-only bones; use speed-scaled walk until clips are rebuilt.
				controller.setAnimation(RawAnimation.begin().then("walk", Animation.LoopType.LOOP));
				if (this.isAggressive() || currentSpeed > speedThreshold) {
					controller.setAnimationSpeed(1.1);
				} else {
					controller.setAnimationSpeed(1.0);
				}
			} else {
				if (this.isAggressive()) {
					controller.setAnimation(RawAnimation.begin().then("posture", Animation.LoopType.LOOP));
					controller.setAnimationSpeed(1.0);
				} else {
					controller.setAnimation(RawAnimation.begin().then("idle", Animation.LoopType.LOOP));
					controller.setAnimationSpeed(1.0);
				}
			}
		}

		return PlayState.CONTINUE;
	}

	@Override
	public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
		controllers.add(new AnimationController<>(this, "controller", 2, this::predicate));
		controllers.add(LOAnimations.genericAttackAnimation(this, LOAnimations.ATTACK));
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return this.geoCache;
	}

	@Override
	public void positionRider(Entity entity, Entity.MoveFunction moveFunction) {
		if (this.hasPassenger(entity)) {

			double offsetX = 0;
			double offsetY = 1.1;
			double offsetZ = -0.055;

			if (this.getBreed() == 10) {
				offsetY = 1.4;
			}

			if (this.isMale()) {
				if (this.isMeatBreed()) {
					offsetY = 1.3;
				} else if (this.isNormalBreed()) {
					offsetY = 1.0;
				} else if (this.isMiniBreed()) {
					offsetY = 0.8;
				}
			} else if (this.isFemale()) {
				if (this.isMeatBreed()) {
					offsetY = 1.2;
				} else if (this.isNormalBreed()) {
					offsetY = 0.7;
				} else if (this.isMiniBreed()) {
					offsetY = 0.4;
				}
			} else {
				if (this.isMale()) {
					if (this.isMeatBreed()) {
						offsetY = 1.0;
					} else if (this.isNormalBreed()) {
						offsetY = 0.8;
					} else if (this.isMiniBreed()) {
						offsetY = 0.6;
					}
				} else if (this.isFemale()) {
					if (this.isMeatBreed()) {
						offsetY = 0.9;
					} else if (this.isNormalBreed()) {
						offsetY = 0.6;
					} else if (this.isMiniBreed()) {
						offsetY = 0.4;
					}
				}
			}

			double radYaw = Math.toRadians(this.getYRot());

			double offsetXRotated = offsetX * Math.cos(radYaw) - offsetZ * Math.sin(radYaw);
			double offsetYRotated = offsetY;
			double offsetZRotated = offsetX * Math.sin(radYaw) + offsetZ * Math.cos(radYaw);

			double x = this.getX() + offsetXRotated;
			double y = this.getY() + offsetYRotated;
			double z = this.getZ() + offsetZRotated;

			entity.setPos(x, y, z);
		}
	}

	public boolean isFollower() {
		return this.leader != null && this.leader.isAlive();
	}

	public OCow startFollowing(OCow cow) {
		this.leader = cow;
		cow.addFollower();
		return cow;
	}

	public void stopFollowing() {
		if (this.leader != null) {
			this.leader.removeFollower();
			this.leader = null;
		}
	}

	public void addFollower() {
		++this.herdSize;
	}

	public void removeFollower() {
		--this.herdSize;
	}

	public boolean canBeFollowed() {
		return this.hasFollowers() && this.herdSize < this.getMaxHerdSize();
	}

	public int getMaxHerdSize() {
		return LivestockOverhaulCommonConfig.COW_HERD_MAX.get();
	}

	public boolean hasFollowers() {
		return this.herdSize > 1;
	}

	public boolean inRangeOfLeader() {
		return this.distanceToSqr(this.leader) <= 120.0D;
	}

	public void pathToLeader() {
		if (this.isFollower()) {
			this.getNavigation().moveTo(this.leader, 1.0D);
		}

	}

	public void addFollowers(Stream<? extends OCow> p_27534_) {
		p_27534_.limit((long)(this.getMaxHerdSize() - this.herdSize)).filter((cow) -> {
			return cow != this;
		}).forEach((cow) -> {
			cow.startFollowing(this);
		});
	}

	public boolean isFineQuality() {
        return this.getQuality() <= 25;
	}

	public boolean isGreatQuality() {
		return this.getQuality() > 25 && this.getQuality() <= 50;
	}

	public boolean isFantasticQuality() {
		return this.getQuality() > 50 && this.getQuality() <= 75;
	}

	public boolean isExquisiteQuality() {
		return this.getQuality() > 75 && this.getQuality() <= 100;
	}

	public void setAttackDamage() {
		if (this.getBreed() == 11) {
			if (this.isGreatQuality()) {
				this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(2F);
			} else if (this.isFantasticQuality()) {
				this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(3.5F);
			} else if (this.isExquisiteQuality()) {
				this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(5F);
			} else {
				this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(1.5F);
			}
		} else {
			this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(1.5F);
		}
	}

	public int replenishMilkCounter = 0;

	public void tick() {
		super.tick();
		if (this.hasFollowers() && this.level().random.nextInt(200) == 1) {
			List<? extends OCow> list = this.level().getEntitiesOfClass(this.getClass(), this.getBoundingBox().inflate(20.0D, 20.0D, 20.0D));
			if (list.size() <= 1) {
				this.herdSize = 1;
			}
		}

		replenishMilkCounter++;

		if (!this.isDairyBreed()) {
			if (LivestockOverhaulCommonConfig.QUALITY.get()) {
				if (this.isFineQuality()) {
					if (replenishMilkCounter >= LivestockOverhaulCommonConfig.MILKING_COOLDOWN.get()) {
						this.setMilked(false);
					}
				} else if (this.isGreatQuality()) {
					if (replenishMilkCounter >= (LivestockOverhaulCommonConfig.MILKING_COOLDOWN.get() / 1.3)) {
						this.setMilked(false);
					}
				} else if (this.isFantasticQuality()) {
					if (replenishMilkCounter >= (LivestockOverhaulCommonConfig.MILKING_COOLDOWN.get() / 2)) {
						this.setMilked(false);
					}
				} else if (this.isExquisiteQuality()) {
					if (replenishMilkCounter >= (LivestockOverhaulCommonConfig.MILKING_COOLDOWN.get() / 2.5)) {
						this.setMilked(false);
					}
				}
			} else {
				if (replenishMilkCounter >= LivestockOverhaulCommonConfig.MILKING_COOLDOWN.get()) {
					this.setMilked(false);
				}
			}
		}

		if (this.isDairyBreed()) {
			if (LivestockOverhaulCommonConfig.QUALITY.get()) {
				if (this.isFineQuality()) {
					if (replenishMilkCounter >= LivestockOverhaulCommonConfig.DAIRY_MILKING_COOLDOWN.get()) {
						this.setMilked(false);
					}
				} else if (this.isGreatQuality()) {
					if (replenishMilkCounter >= (LivestockOverhaulCommonConfig.DAIRY_MILKING_COOLDOWN.get() / 1.3)) {
						this.setMilked(false);
					}
				} else if (this.isFantasticQuality()) {
					if (replenishMilkCounter >= (LivestockOverhaulCommonConfig.DAIRY_MILKING_COOLDOWN.get() / 2)) {
						this.setMilked(false);
					}
				} else if (this.isExquisiteQuality()) {
					if (replenishMilkCounter >= (LivestockOverhaulCommonConfig.DAIRY_MILKING_COOLDOWN.get() / 2.5)) {
						this.setMilked(false);
					}
				}
			} else {
				if (replenishMilkCounter >= LivestockOverhaulCommonConfig.DAIRY_MILKING_COOLDOWN.get()) {
					this.setMilked(false);
				}
			}
		}

	}

	@Override
	public InteractionResult mobInteract(Player player, InteractionHand hand) {
		ItemStack itemStack = player.getItemInHand(hand);
		Item item = itemStack.getItem();

		if (itemStack.is(LOItems.BREED_OSCILLATOR.get()) && player.getAbilities().instabuild && this.getBreed() >= 0 && this.getBreed() < 10) {
			if (player.isShiftKeyDown()) {
				if (this.getBreed() > 0) {
					this.setBreed(this.getBreed() - 1);
					this.playSound(SoundEvents.BEEHIVE_EXIT, 0.5f, 1f);
					return InteractionResult.SUCCESS;
				}
			}
			if (this.getBreed() < 9) {
				CowBreed.Breed currentBreed = CowBreed.Breed.values()[this.getBreed()];
				CowBreed.Breed nextBreed = currentBreed.next();
				this.setBreed(nextBreed.ordinal());
				this.playSound(SoundEvents.BEEHIVE_EXIT, 0.5f, 1f);
				return InteractionResult.SUCCESS;
			}
		}

		if (this.isFood(itemStack)) {
			int i = this.getAge();
			if (!this.level().isClientSide && i == 0 && this.canFallInLove()) {
				this.usePlayerItem(player, hand, itemStack);
				this.setInLove(player);
				return InteractionResult.SUCCESS;
			}

			if (this.isBaby()) {
				this.usePlayerItem(player, hand, itemStack);
				this.ageUp(getSpeedUpSecondsWhenFeeding(-i), true);
				return InteractionResult.sidedSuccess(this.level().isClientSide);
			}

			if (this.level().isClientSide) {
				return InteractionResult.CONSUME;
			}
		}

		if (item instanceof BrandTagItem) {
			setTagged(true);
			this.playSound(SoundEvents.SHEEP_SHEAR, 0.5f, 1f);
			BrandTagItem tagItem = (BrandTagItem)item;
			DyeColor color = tagItem.getColor();
				if (color != this.getBrandTagColor()) {
					this.setBrandTagColor(color);
				if (!player.getAbilities().instabuild) {
					itemStack.shrink(1);
				}
				return InteractionResult.sidedSuccess(this.level().isClientSide);
			}
		}

		if (itemStack.is(LOTags.Items.SHEARS)) {
			if (this.isTagged() || this.isHarnessed() || this.isBelled()) {
				if (this.isTagged()) {
					this.setTagged(false);
				}
				if (this.isHarnessed()) {
					this.setHarnessed(false);
					spawnAtLocation(LOItems.RODEO_HARNESS.get());
				}
				if (this.isBelled()) {
					this.setBelled(false);
					spawnAtLocation(Items.BELL);
				}
				this.playSound(SoundEvents.SHEEP_SHEAR, 0.5f, 1f);
				return InteractionResult.sidedSuccess(this.level().isClientSide);
			}
		}

		if (itemStack.is(LOItems.GENDER_TEST_STRIP.get()) && this.isFemale()) {
			player.playSound(SoundEvents.BEEHIVE_EXIT, 1.0F, 1.0F);
			ItemStack itemstack1 = ItemUtils.createFilledResult(itemStack, player, LOItems.FEMALE_GENDER_TEST_STRIP.get().getDefaultInstance());
			player.setItemInHand(hand, itemstack1);
			return InteractionResult.SUCCESS;
		}

		if (itemStack.is(LOItems.GENDER_TEST_STRIP.get()) && this.isMale()) {
			player.playSound(SoundEvents.BEEHIVE_EXIT, 1.0F, 1.0F);
			ItemStack itemstack1 = ItemUtils.createFilledResult(itemStack, player, LOItems.MALE_GENDER_TEST_STRIP.get().getDefaultInstance());
			player.setItemInHand(hand, itemstack1);
			return InteractionResult.SUCCESS;
		}

		if(itemStack.is(LOItems.RODEO_HARNESS.get()) && !this.isHarnessed()) {
			if(!this.level().isClientSide) {
				this.level().gameEvent(this, GameEvent.EQUIP, this.position());
				itemStack.shrink(1);
				this.setHarnessed(true);
			}
			return InteractionResult.sidedSuccess(this.level().isClientSide);
		}

		if(itemStack.is(Items.BELL) && !this.isBelled()) {
			if(!this.level().isClientSide) {
				this.level().gameEvent(this, GameEvent.EQUIP, this.position());
				itemStack.shrink(1);
				this.setBelled(true);
			}
			return InteractionResult.sidedSuccess(this.level().isClientSide);
		}

		if (this.isFood(itemStack)) {
			if (!player.getAbilities().instabuild) {
				itemStack.shrink(1);
			}

			if (!this.isTamed() && this.random.nextInt(3) == 0 && !net.neoforged.neoforge.event.EventHooks.onAnimalTame(this, player)) {
				this.setTamed(true);
			}

			return InteractionResult.SUCCESS;
		}

		if (itemStack.is(Items.BUCKET) && !this.isBaby()) {
			if (!wasMilked()) {
					if ((!LivestockOverhaulCommonConfig.GENDERS_AFFECT_BIPRODUCTS.get()) || (LivestockOverhaulCommonConfig.GENDERS_AFFECT_BIPRODUCTS.get() && this.isFemale())) {
						player.playSound(SoundEvents.COW_MILK, 1.0F, 1.0F);
						ItemStack itemstack1 = ItemUtils.createFilledResult(itemStack, player, Items.MILK_BUCKET.getDefaultInstance());
						player.setItemInHand(hand, itemstack1);
						replenishMilkCounter = 0;
						setMilked(true);
					}
				}
			return InteractionResult.sidedSuccess(this.level().isClientSide);
		}

		return super.mobInteract(player, hand);
	}

	public SoundEvent getAmbientSound() {
		super.getAmbientSound();
		return SoundEvents.COW_AMBIENT;
	}

	public SoundEvent getDeathSound() {
		super.getDeathSound();
		return SoundEvents.COW_DEATH;
	}

	public SoundEvent getHurtSound(DamageSource p_30720_) {
		super.getHurtSound(p_30720_);
		return SoundEvents.COW_HURT;
	}

	public void playStepSound(BlockPos p_28301_, BlockState p_28302_) {
		if (this.isBelled() && LivestockOverhaulCommonConfig.COW_BELL_SOUND.get()) {
			this.playSound(SoundEvents.BELL_BLOCK, 0.3F, 1.3F);
		} else {
			this.playSound(SoundEvents.COW_STEP, 0.15F, 1.0F);
		}
	}

	//mostly for Spanish Fighting Bulls, cow postures before attacking
	public boolean doneWaiting = false;
	public boolean isDoneWaiting() {
		return this.doneWaiting;
	}
	public void setDoneWaiting(boolean doneWaiting) {
		this.doneWaiting = doneWaiting;
	}

	// Generates the base texture
	public static final EntityDataAccessor<Integer> BREED = SynchedEntityData.defineId(OCow.class, EntityDataSerializers.INT);
	public int getBreedLocation() {
		return CowBreed.Breed.values().length;
	}
	public int getBreed() {
		return this.entityData.get(BREED);
	}
	public void setBreed(int breed) {
		this.entityData.set(BREED, breed);
	}

	private transient boolean breedVariantAssigned;

	public static final EntityDataAccessor<Integer> VARIANT = SynchedEntityData.defineId(OCow.class, EntityDataSerializers.INT);
	public ResourceLocation getTextureLocation() {
		if (!LivestockOverhaulClientConfig.SIMPLE_MODELS.get()) {
			return OCowModel.Variant.variantFromOrdinal(getVariant()).resourceLocation;
		} else {
			return OCowModel.SVariant.variantFromOrdinal(getVariant()).resourceLocation;
		}
	}
	public int getVariant() {
		return this.entityData.get(VARIANT);
	}
	public void setVariant(int variant) {
		this.entityData.set(VARIANT, variant);
	}
	private void assignBreedVariant(int variant) {
		this.breedVariantAssigned = true;
		this.setVariant(variant);
	}


	public static final EntityDataAccessor<Integer> OVERLAY = SynchedEntityData.defineId(OCow.class, EntityDataSerializers.INT);
	public String getOverlayLocation() {return BovineMarkingOverlay.overlayFromOrdinal(getOverlayVariant()).resourceLocation.toString();}
	public int getOverlayVariant() {
		return this.entityData.get(OVERLAY);
	}
	public void setOverlayVariant(int overlayVariant) {
		this.entityData.set(OVERLAY, overlayVariant);
	}

	public static final EntityDataAccessor<Integer> HORN_TYPE = SynchedEntityData.defineId(OCow.class, EntityDataSerializers.INT);
	public int getHornVariant() {
		return this.entityData.get(HORN_TYPE);
	}
	public void setHornVariant(int hornVariant) {
		this.entityData.set(HORN_TYPE, hornVariant);
	}

	protected static final EntityDataAccessor<Integer> BRAND_TAG_COLOR = SynchedEntityData.defineId(OCow.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Boolean> TAGGED = SynchedEntityData.defineId(OCow.class, EntityDataSerializers.BOOLEAN);
	public DyeColor getBrandTagColor() {
		return DyeColor.byId(this.entityData.get(BRAND_TAG_COLOR));
	}
	public void setBrandTagColor(DyeColor color) {
		this.entityData.set(BRAND_TAG_COLOR, color.getId());
	}
	@Override
	public boolean isTaggable() {
		return this.isAlive() && !this.isBaby();
	}
	@Override
	public boolean isTagged() {
		return this.entityData.get(TAGGED);
	}
	public void setTagged(boolean tagged) {
		this.entityData.set(TAGGED, tagged);
	}
	@Override
	public void equipTag(@javax.annotation.Nullable SoundSource soundSource) {
		if(soundSource != null) {
			this.level().playSound(null, this, SoundEvents.BOOK_PAGE_TURN, soundSource, 0.5f, 1f);
		}
	}

	public static final EntityDataAccessor<Boolean> MILKED = SynchedEntityData.defineId(OCow.class, EntityDataSerializers.BOOLEAN);
	public boolean wasMilked() {
		return this.entityData.get(MILKED);
	}
	public void setMilked(boolean milked) {
		this.entityData.set(MILKED, milked);
	}

	public static final EntityDataAccessor<Boolean> HARNESSED = SynchedEntityData.defineId(OCow.class, EntityDataSerializers.BOOLEAN);
	public boolean isHarnessed() {
		return this.entityData.get(HARNESSED);
	}
	public void setHarnessed(boolean harnessed) {
		this.entityData.set(HARNESSED, harnessed);
	}

	public static final EntityDataAccessor<Boolean> BELLED = SynchedEntityData.defineId(OCow.class, EntityDataSerializers.BOOLEAN);
	public boolean isBelled() {
		return this.entityData.get(BELLED);
	}
	public void setBelled(boolean belled) {
		this.entityData.set(BELLED, belled);
	}

	public static final EntityDataAccessor<Integer> QUALITY = SynchedEntityData.defineId(OCow.class, EntityDataSerializers.INT);
	public int getQuality() {
		return this.entityData.get(QUALITY);
	}
	public void setQuality(int i) {
		this.entityData.set(QUALITY, i);
	}

	@Override
	public void readAdditionalSaveData(CompoundTag tag) {
		super.readAdditionalSaveData(tag);

		if(tag.contains("Quality")) {
			this.setQuality(tag.getInt("Quality"));
		}

		if (tag.contains("Breed")) {
			setBreed(tag.getInt("Breed"));
		}

		if (tag.contains("Variant")) {
			setVariant(tag.getInt("Variant"));
		}

		if (tag.contains("Overlay")) {
			setOverlayVariant(tag.getInt("Overlay"));
		}

		if (tag.contains("HornType")) {
			setHornVariant(tag.getInt("HornType"));
		}

		if (tag.contains("Gender")) {
			this.setGender(tag.getInt("Gender"));
		}

		if (tag.contains("MilkedTime")) {
			this.replenishMilkCounter = tag.getInt("MilkedTime");
		}

		if (tag.contains("Milked")) {
			setMilked(tag.getBoolean("Milked"));
		}

		if(tag.contains("Tagged")) {
			this.setTagged(tag.getBoolean("Tagged"));
		}

		this.setBrandTagColor(DyeColor.byId(tag.getInt("BrandTagColor")));

		if(tag.contains("Harnessed")) {
			this.setHarnessed(tag.getBoolean("Harnessed"));
		}

		if(tag.contains("Belled")) {
			this.setBelled(tag.getBoolean("Belled"));
		}
	}

	@Override
	public void addAdditionalSaveData(CompoundTag tag) {
		super.addAdditionalSaveData(tag);
		tag.putInt("Quality", this.getQuality());
		tag.putInt("Breed", getBreed());
		tag.putInt("Variant", getVariant());
		tag.putInt("Overlay", getOverlayVariant());
		tag.putInt("HornType", getHornVariant());
		tag.putInt("Gender", this.getGender());
		tag.putBoolean("Milked", this.wasMilked());
		tag.putInt("MilkedTime", this.replenishMilkCounter);
		tag.putBoolean("Tagged", this.isTagged());
		tag.putByte("BrandTagColor", (byte)this.getBrandTagColor().getId());
		tag.putBoolean("Harnessed", this.isHarnessed());
		tag.putBoolean("Belled", this.isBelled());
	}

	@Override
	@Nullable
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor serverLevelAccessor, DifficultyInstance instance, MobSpawnType spawnType, @Nullable SpawnGroupData data) {
		if (data == null) {
			data = new AgeableMobGroupData(0.2F);
		}
		Random random = new Random();

		this.setBreed(random.nextInt(CowBreed.Breed.values().length));

		if (LivestockOverhaulCommonConfig.QUALITY.get()) {
			this.setQuality(random.nextInt(30));
		}

		if (this.getBreed() == 10) {
			this.setGender(1);
		} else {
			this.setGender(random.nextInt(OCow.Gender.values().length));
		}

		if (LivestockOverhaulCommonConfig.SPAWN_BY_BREED.get()) {
			this.setColorByBreed();
			this.setMarkingByBreed();
			this.setHornsByBreed();
		} else {
			this.setVariant(random.nextInt(OCowModel.Variant.values().length));
			this.setOverlayVariant(random.nextInt(BovineMarkingOverlay.values().length));
			this.setHornVariant(random.nextInt(OCow.BreedHorns.values().length));
		}

		this.setAttackDamage();
		return super.finalizeSpawn(serverLevelAccessor, instance, spawnType, data);
	}

	@Override
	public void defineSynchedData(SynchedEntityData.Builder builder) {
		super.defineSynchedData(builder);
		builder.define(QUALITY, 0);
		builder.define(BREED, 0);
		builder.define(VARIANT, 0);
		builder.define(OVERLAY, 0);
		builder.define(GENDER, 0);
		builder.define(HORN_TYPE, 0);
		builder.define(BRAND_TAG_COLOR, DyeColor.YELLOW.getId());
		builder.define(TAGGED, false);
		builder.define(MILKED, false);
		builder.define(HARNESSED, false);
		builder.define(BELLED, false);
	}

	public enum Gender {
		FEMALE,
		MALE
	}
	public boolean isFemale() {
		return this.getGender() == 0;
	}
	public boolean isMale() {
		return this.getGender() == 1;
	}
	public static final EntityDataAccessor<Integer> GENDER = SynchedEntityData.defineId(OCow.class, EntityDataSerializers.INT);
	public int getGender() {
		return this.entityData.get(GENDER);
	}
	public void setGender(int gender) {
		this.entityData.set(GENDER, gender);
	}
	public boolean canParent() {
		return !this.isBaby() && this.isInLove();
	}

	public boolean canMate(Animal animal) {
		if (animal == this) {
			return false;
		} else if (!(animal instanceof OCow)) {
			return false;
		} else {
			if (!LivestockOverhaulCommonConfig.GENDERS_AFFECT_BREEDING.get()) {
				return this.canParent() && ((OCow) animal).canParent();
			} else {
				OCow partner = (OCow) animal;
				if (this.canParent() && partner.canParent() && this.getGender() != partner.getGender()) {
					return isFemale();
				}
			}
		}
		return false;
	}

	@Override
	public AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
		OCow calf;
		OCow partner = (OCow) ageableMob;
		calf = EntityTypes.O_COW_ENTITY.get().create(serverLevel);
		if (calf == null) {
			return null;
		}

		int breedChance = this.random.nextInt(100);
		int breed;
		if (breedChance < ((100 - LivestockOverhaulCommonConfig.BREED_CHANCE.get()) / 2)) {
			breed = this.getBreed();
		} else if (breedChance < (100 - LivestockOverhaulCommonConfig.BREED_CHANCE.get())) {
			breed = partner.getBreed();
		} else {
			breed = this.random.nextInt(CowBreed.Breed.values().length);
		}
		calf.setBreed(breed);

		if (!(breedChance <= LivestockOverhaulCommonConfig.BREED_CHANCE.get())) {
			int variantChance = this.random.nextInt(100);
			int variant;
			if (variantChance < ((100 - LivestockOverhaulCommonConfig.COAT_CHANCE.get()) / 2)) {
				variant = this.getVariant();
			} else if (variantChance < (100 - LivestockOverhaulCommonConfig.COAT_CHANCE.get())) {
				variant = partner.getVariant();
			} else {
				variant = this.random.nextInt(OCowModel.Variant.values().length);
			}
			calf.setVariant(variant);
		} else if (random.nextDouble() < 0.5) {
			calf.setColorByBreed();
		}

		if (!(breedChance <= LivestockOverhaulCommonConfig.BREED_CHANCE.get())) {
			int overlayChance = this.random.nextInt(100);
			int overlay;
			if (overlayChance < ((100 - LivestockOverhaulCommonConfig.MARKING_CHANCE.get()) / 2)) {
				overlay = this.getOverlayVariant();
			} else if (overlayChance < (100 - LivestockOverhaulCommonConfig.MARKING_CHANCE.get())) {
				overlay = partner.getOverlayVariant();
			} else {
				overlay = this.random.nextInt(BovineMarkingOverlay.values().length);
			}
			calf.setOverlayVariant(overlay);
		} else if (random.nextDouble() < 0.5) {
			calf.setMarkingByBreed();
		}

		if (!(breedChance <= LivestockOverhaulCommonConfig.BREED_CHANCE.get())) {
			int hornsChance = this.random.nextInt(100);
			int hornType;
			if (hornsChance < ((100 - LivestockOverhaulCommonConfig.OTHER_CHANCE.get()) / 2)) {
				hornType = this.getHornVariant();
			} else if (hornsChance < (100 - LivestockOverhaulCommonConfig.OTHER_CHANCE.get())) {
				hornType = partner.getHornVariant();
			} else {
				hornType = this.random.nextInt(OCow.BreedHorns.values().length);
			}
			calf.setHornVariant(hornType);
		} else if (random.nextDouble() < 0.5) {
			calf.setHornsByBreed();
		}

		if (calf.getBreed() == 10) {
			calf.setGender(1);
		} else {
			calf.setGender(random.nextInt(OCow.Gender.values().length));
		}

		if (LivestockOverhaulCommonConfig.QUALITY.get()) {
			int qualAvg = (this.getQuality() + partner.getQuality()) / 2;
			if (random.nextDouble() <= 0.05) {
				calf.setQuality(qualAvg + random.nextInt(50));
			} else if (random.nextDouble() >= 0.05 && random.nextDouble() <= 0.25) {
				calf.setQuality(qualAvg + random.nextInt(25));
			} else if (random.nextDouble() >= 0.25 && random.nextDouble() <= 0.60) {
				calf.setQuality(qualAvg + random.nextInt(10));
			} else {
				calf.setQuality(qualAvg + random.nextInt(5));
			}
		}

		// Prevent quality overflow from wrapping tier buckets.
		if (calf.getQuality() > 100) {
			calf.setQuality(100);
		}

		calf.setAttackDamage();
		return calf;
	}

	@Override
	public void dropCustomDeathLoot(ServerLevel p_33574_, DamageSource p_33575_, boolean p_33576_) {
		super.dropCustomDeathLoot(p_33574_, p_33575_, p_33576_);
		Random random = new Random();

		if (!LivestockOverhaulCommonConfig.USE_VANILLA_LOOT.get() || !ModList.get().isLoaded("tfc")) {
			if (this.isMeatBreed()) {
				if (random.nextDouble() < 0.40) {
					this.spawnAtLocation(new ItemStack(Items.BEEF, 2), 0F);
					this.spawnAtLocation(new ItemStack(Items.LEATHER, 2), 0F);
				} else if (random.nextDouble() > 0.40) {
					this.spawnAtLocation(new ItemStack(Items.BEEF, 1), 0F);
					this.spawnAtLocation(new ItemStack(Items.LEATHER, 1), 0F);
				}
			}

			if (this.isNormalBreed()) {
				if (random.nextDouble() < 0.15) {
					this.spawnAtLocation(new ItemStack(Items.BEEF, 1), 0F);
					this.spawnAtLocation(new ItemStack(Items.LEATHER, 1), 0F);
				}
			}

			if (LivestockOverhaulCommonConfig.QUALITY.get()) {
				if (this.isExquisiteQuality()) {
					this.spawnAtLocation(new ItemStack(Items.BEEF, 3), 0F);
					this.spawnAtLocation(new ItemStack(Items.LEATHER, 3), 0F);
				} else if (this.isFantasticQuality()) {
					this.spawnAtLocation(new ItemStack(Items.BEEF, 2), 0F);
					this.spawnAtLocation(new ItemStack(Items.LEATHER, 2), 0F);
				} else if (this.isGreatQuality()) {
					this.spawnAtLocation(new ItemStack(Items.BEEF, 1), 0F);
					this.spawnAtLocation(new ItemStack(Items.LEATHER, 1), 0F);
				}
			}

		}
	}

	public void setColorByBreed() {

		final double appearanceRoll = random.nextDouble();
		this.breedVariantAssigned = false;


		if (this.getBreed() == 0) { //angus
			if (appearanceRoll < 0.05) {
				this.assignBreedVariant(random.nextInt(OCowModel.Variant.values().length));
			} else if (appearanceRoll > 0.05) {
				this.assignBreedVariant(0);
			}
		}

		if (this.getBreed() == 1) {
			if (appearanceRoll < 0.05) { //longhorn
				this.assignBreedVariant(random.nextInt(OCowModel.Variant.values().length));
			} else if (appearanceRoll > 0.05) {
				int[] variants = {2, 3, 4, 5, 7, 8, 10, 12};
				int randomIndex = new Random().nextInt(variants.length);
				this.assignBreedVariant(variants[randomIndex]);
			}
		}

		if (this.getBreed() == 2) {
			if (appearanceRoll < 0.05) { //brahman
				this.assignBreedVariant(random.nextInt(OCowModel.Variant.values().length));
			} else if (appearanceRoll > 0.05) {
				int[] variants = {1, 2, 3, 4, 5, 6, 8, 9, 11, 12, 13};
				int randomIndex = new Random().nextInt(variants.length);
				this.assignBreedVariant(variants[randomIndex]);
			}
		}

		if (this.getBreed() == 3) { //mini
			this.assignBreedVariant(random.nextInt(OCowModel.Variant.values().length));
		}

		if (this.getBreed() == 4) { //watusi
			if (appearanceRoll < 0.05) {
				this.assignBreedVariant(random.nextInt(OCowModel.Variant.values().length));
			} else if (appearanceRoll > 0.05) {
				int[] variants = {2, 3, 5, 10, 12};
				int randomIndex = new Random().nextInt(variants.length);
				this.assignBreedVariant(variants[randomIndex]);
			}
		}

		if (this.getBreed() == 5) {
			if (appearanceRoll < 0.05) { //corriente
				this.assignBreedVariant(random.nextInt(OCowModel.Variant.values().length));
			} else if (appearanceRoll > 0.05) {
				int[] variants = {0, 2, 3, 4, 5, 8, 9, 10, 12};
				int randomIndex = new Random().nextInt(variants.length);
				this.assignBreedVariant(variants[randomIndex]);
			}
		}

		if (this.getBreed() == 6) {
			if (appearanceRoll < 0.05) { //holstein
				this.assignBreedVariant(random.nextInt(OCowModel.Variant.values().length));
			} else if (appearanceRoll > 0.05) {
				int[] variants = {0, 2, 9, 10};
				int randomIndex = new Random().nextInt(variants.length);
				this.assignBreedVariant(variants[randomIndex]);
			}
		}

		if (this.getBreed() == 7) {
			if (appearanceRoll < 0.05) { //jersey
				this.assignBreedVariant(random.nextInt(OCowModel.Variant.values().length));
			} else if (appearanceRoll > 0.05) {
				int[] variants = {2, 8};
				int randomIndex = new Random().nextInt(variants.length);
				this.assignBreedVariant(variants[randomIndex]);
			}
		}

		if (this.getBreed() == 8) {
			if (appearanceRoll < 0.05) { //hereford
				this.assignBreedVariant(random.nextInt(OCowModel.Variant.values().length));
			} else if (appearanceRoll > 0.05) {
				int[] variants = {2, 5, 10, 12};
				int randomIndex = new Random().nextInt(variants.length);
				this.assignBreedVariant(variants[randomIndex]);
			}
		}

		if (this.getBreed() == 9) { //highland
			this.assignBreedVariant(random.nextInt(OCowModel.Variant.values().length));
		}

		if (this.getBreed() == 10) {
			if (appearanceRoll < 0.15) { //ox
				this.assignBreedVariant(random.nextInt(OCowModel.Variant.values().length));
			} else if (appearanceRoll > 0.15) {
				int[] variants = {2, 8, 10, 12};
				int randomIndex = new Random().nextInt(variants.length);
				this.assignBreedVariant(variants[randomIndex]);
			}
		}

		if (this.getBreed() == 11) {
			if (appearanceRoll < 0.15) { //spanish fighting
				this.assignBreedVariant(random.nextInt(OCowModel.Variant.values().length));
			} else if (appearanceRoll > 0.15) {
				this.assignBreedVariant(0);
			}
		}

	
		if (!this.breedVariantAssigned) {
			this.setVariant(random.nextInt(OCowModel.Variant.values().length));
		}
	}

	public void setMarkingByBreed() {

		final double appearanceRoll = random.nextDouble();

		if (this.getBreed() == 0) { //angus
			if (appearanceRoll < 0.05) {
				this.setOverlayVariant(random.nextInt(BovineMarkingOverlay.values().length));
			} else if (appearanceRoll > 0.05) {
				this.setOverlayVariant(0);
			}
		}

		if (this.getBreed() == 1) {
			if (appearanceRoll < 0.05) { //longhorn
				this.setOverlayVariant(random.nextInt(BovineMarkingOverlay.values().length));
			} else if (appearanceRoll > 0.05) {
				int[] variants = {9, 10, 11, 13, 14, 15, 17, 18, 19};
				int randomIndex = new Random().nextInt(variants.length);
				this.setOverlayVariant(variants[randomIndex]);
			}
		}

		if (this.getBreed() == 2) {
			if (appearanceRoll < 0.05) { //brahman
				this.setOverlayVariant(random.nextInt(BovineMarkingOverlay.values().length));
			} else if (appearanceRoll > 0.05) {
				this.setOverlayVariant(0);
			}
		}

		if (this.getBreed() == 3) { //mini
			this.setOverlayVariant(random.nextInt(BovineMarkingOverlay.values().length));
		}

		if (this.getBreed() == 4) { //watusi
			if (appearanceRoll < 0.05) {
				this.setOverlayVariant(random.nextInt(BovineMarkingOverlay.values().length));
			} else if (appearanceRoll > 0.05) {
				int[] variants = {0, 9, 10, 11, 13, 14, 15, 17, 18, 19};
				int randomIndex = new Random().nextInt(variants.length);
				this.setOverlayVariant(variants[randomIndex]);
			}
		}

		if (this.getBreed() == 5) {
			if (appearanceRoll < 0.05) { //corriente
				this.setOverlayVariant(random.nextInt(BovineMarkingOverlay.values().length));
			} else if (appearanceRoll > 0.05) {
				int[] variants = {0, 1, 5, 9, 13, 17, 21, 22, 23};
				int randomIndex = new Random().nextInt(variants.length);
				this.setOverlayVariant(variants[randomIndex]);
			}
		}

		if (this.getBreed() == 6) {
			if (appearanceRoll < 0.05) { //holstein
				this.setOverlayVariant(random.nextInt(BovineMarkingOverlay.values().length));
			} else if (appearanceRoll > 0.05) {
				int[] variants = {2, 3, 5, 10, 11, 18, 19, 21, 22, 23, 24};
				int randomIndex = new Random().nextInt(variants.length);
				this.setOverlayVariant(variants[randomIndex]);
			}
		}

		if (this.getBreed() == 7) {
			if (appearanceRoll < 0.05) { //jersey
				this.setOverlayVariant(random.nextInt(BovineMarkingOverlay.values().length));
			} else if (appearanceRoll > 0.05) {
				int[] variants = {0, 21, 22, 23};
				int randomIndex = new Random().nextInt(variants.length);
				this.setOverlayVariant(variants[randomIndex]);
			}
		}

		if (this.getBreed() == 8) {
			if (appearanceRoll < 0.05) { //hereford
				this.setOverlayVariant(random.nextInt(BovineMarkingOverlay.values().length));
			} else if (appearanceRoll > 0.05) {
				this.setOverlayVariant(25);
			}
		}

		if (this.getBreed() == 9) { //highland
			if (appearanceRoll < 0.05) {
				this.setOverlayVariant(random.nextInt(BovineMarkingOverlay.values().length));
			} else if (appearanceRoll > 0.05) {
				this.setOverlayVariant(0);
			}
		}

		if (this.getBreed() == 10) { //ox
			if (appearanceRoll < 0.15) {
				this.setOverlayVariant(random.nextInt(BovineMarkingOverlay.values().length));
			} else if (appearanceRoll > 0.15) {
				this.setOverlayVariant(0);
			}
		}

		if (this.getBreed() == 11) { //spanish fighting
			if (appearanceRoll < 0.15) {
				this.setOverlayVariant(random.nextInt(BovineMarkingOverlay.values().length));
			} else if (appearanceRoll > 0.15) {
				this.setOverlayVariant(0);
			}
		}

	}

	public void setHornsByBreed() {

		final double appearanceRoll = random.nextDouble();

		if (this.getBreed() == 0) { //angus
			if (appearanceRoll < 0.05) {
				this.setHornVariant(random.nextInt(BreedHorns.values().length));
			} else if (appearanceRoll > 0.05) {
				this.setHornVariant(0);
			}
		}

		if (this.getBreed() == 1) {
			if (appearanceRoll < 0.05) { //longhorn
				this.setHornVariant(random.nextInt(BreedHorns.values().length));
			} else if (appearanceRoll > 0.05) {
				int[] variants = {2, 3, 4};
				int randomIndex = new Random().nextInt(variants.length);
				this.setHornVariant(variants[randomIndex]);
			}
		}

		if (this.getBreed() == 2) {
			if (appearanceRoll < 0.05) { //brahman
				this.setHornVariant(random.nextInt(BreedHorns.values().length));
			} else if (appearanceRoll > 0.05) {
				int[] variants = {0, 8, 9, 10};
				int randomIndex = new Random().nextInt(variants.length);
				this.setHornVariant(variants[randomIndex]);
			}
		}

		if (this.getBreed() == 3) {
			if (appearanceRoll < 0.05) { //mini
				this.setHornVariant(random.nextInt(BreedHorns.values().length));
			} else if (appearanceRoll > 0.05) {
				int[] variants = {0, 8, 10};
				int randomIndex = new Random().nextInt(variants.length);
				this.setHornVariant(variants[randomIndex]);
			}
		}

		if (this.getBreed() == 4) { //watusi
			if (appearanceRoll < 0.05) {
				this.setHornVariant(random.nextInt(BreedHorns.values().length));
			} else if (appearanceRoll > 0.05) {
				int[] variants = {5, 6};
				int randomIndex = new Random().nextInt(variants.length);
				this.setHornVariant(variants[randomIndex]);
			}
		}

		if (this.getBreed() == 5) {
			if (appearanceRoll < 0.05) { //corriente
				this.setHornVariant(random.nextInt(BreedHorns.values().length));
			} else if (appearanceRoll > 0.05) {
				int[] variants = {1, 7, 8, 10};
				int randomIndex = new Random().nextInt(variants.length);
				this.setHornVariant(variants[randomIndex]);
			}
		}

		if (this.getBreed() == 6) {
			if (appearanceRoll < 0.05) { //holstein
				this.setHornVariant(random.nextInt(BreedHorns.values().length));
			} else if (appearanceRoll > 0.05) {
				this.setHornVariant(0);
			}
		}

		if (this.getBreed() == 7) {
			if (appearanceRoll < 0.05) { //jersey
				this.setHornVariant(random.nextInt(BreedHorns.values().length));
			} else if (appearanceRoll > 0.05) {
				this.setHornVariant(0);
			}
		}

		if (this.getBreed() == 8) {
			if (appearanceRoll < 0.05) { //hereford
				this.setHornVariant(random.nextInt(BreedHorns.values().length));
			} else if (appearanceRoll > 0.05) {
				this.setHornVariant(0);
			}
		}

		if (this.getBreed() == 9) {
			if (appearanceRoll < 0.05) { //highland
				this.setHornVariant(random.nextInt(BreedHorns.values().length));
			} else if (appearanceRoll > 0.05) {
				int[] variants = {1, 7, 8, 10};
				int randomIndex = new Random().nextInt(variants.length);
				this.setHornVariant(variants[randomIndex]);
			}
		}

		if (this.getBreed() == 10) {
			if (appearanceRoll < 0.25) { //ox
				this.setHornVariant(random.nextInt(BreedHorns.values().length));
			} else if (appearanceRoll > 0.25) {
				int[] variants = {1, 3, 7, 8, 10};
				int randomIndex = new Random().nextInt(variants.length);
				this.setHornVariant(variants[randomIndex]);
			}
		}

		if (this.getBreed() == 11) {
			if (appearanceRoll < 0.25) { //spanish fighting
				this.setHornVariant(random.nextInt(BreedHorns.values().length));
			} else if (appearanceRoll > 0.25) {
				this.setHornVariant(8);
			}
		}

	}

	public enum BreedHorns {
		NONE,
		CLASSIC_BULL_UPWARDS,
		LONGHORN_FORWARD,
		LONGHORN_UPWARDS,
		LONGHORN_DOWNWARDS,
		WATUSI_STRAIGHT,
		WATUSI_CURVED,
		SMALL_UPWARDS,
		CLASSIC_BULL_FORWARD,
		ZEBU,
		SMALL_FORWARD;

		public static OCow.BreedHorns hornsFromOrdinal(int ordinal) {
			return OCow.BreedHorns.values()[ordinal % OCow.BreedHorns.values().length];
		}
	}


	//ox stuff

	@Override
	public void openInventory(Player player) {
		if(player instanceof ServerPlayer serverPlayer && this.isTamed() && this.getBreed() == 10) {
			serverPlayer.openMenu(new SimpleMenuProvider((containerId, inventory, p) -> {
				return new OxMenu(containerId, inventory, this.inventory, this);
			}, this.getDisplayName()), (data) -> {
				data.writeInt(this.getInventorySize());
				data.writeInt(this.getId());
			});
		}
	}

	@Override
	public boolean isSaddleable() {
		return super.isSaddleable() && this.getBreed() == 10;
	}

	@Override
	public int saddleSlot() {
		return 0;
	}

	@Override
	public boolean canJump() {
		return false;
	}

	public class CowPanicGoal extends PanicGoal {
		public CowPanicGoal(double v) {
			super(OCow.this, v);
		}

		public boolean shouldPanic() {
			return this.mob.isFreezing() || this.mob.isOnFire() || this.mob.getHealth() < this.mob.getMaxHealth() / 3 && this.mob.isAlive();
		}
	}
}
