# Village Context Rules

## Goal

Village proximity should affect breed choice, not base spawn chance.

That means the world still decides whether a horse spawns using normal creature weights.

Then, if the horse is near a village, the breed selector increases the chance of domestic breeds.

## Recommended logic

```text
Horse spawns in valid biome.
Check distance to nearest village or village-like POI.
Apply domestic breed bonus.
Reduce strongly wild breed weights.
Roll breed.
```

## Suggested radius

```text
0-64 blocks:
  strong village influence

65-128 blocks:
  moderate village influence

129+ blocks:
  normal biome-only breed selection
```

## Smooth falloff

A simple falloff model:

```text
villageInfluence = 1.0 - clamp(distance / 128.0, 0.0, 1.0)
```

Then apply:

```text
effectiveWeight = baseWeight + (addedVillageWeight * villageInfluence)
```

For multipliers:

```text
effectiveWeight = effectiveWeight * lerp(1.0, multiplier, villageInfluence)
```

## Domestic breed boost list

The `near_village` context rule in `data/yourmod/horse_breed_rules/default.json` adds weights to domestic breeds such as:

```text
Ardennes
Kladruber
Thoroughbred
Friesian
Irish Cob
Percheron
Selle Francais
Shire
Oldenburger
Standardbred
Trakehner
Boulonnais
Dutch Warmblood
Clydesdale
Lipizzaner
Belgian Draft
Tennessee Walking Horse
Andalusian
```

It also gives smaller boosts to flexible "both" breeds such as:

```text
Morgan
American Quarter
Paint Horse
Appaloosa
Canadian
Welsh
Connemara
Haflinger
Shetland
```

## Wild breed reduction near villages

The template reduces these near villages:

```text
Mustang
Mongolian
Camargue
Arabian
Marwari
Akhal-Teke
```

This does not ban them. It just makes them less likely around settlements.
