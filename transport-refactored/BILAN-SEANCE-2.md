# Bilan de la séance 2

## Choix des stratégies

Les enums `ModeTransport` et `TypeClient` portent respectivement leur tarif et leur remise.
Cela rend le choix facile à suivre ; ajouter une valeur impose de modifier l'enum. Une table
injectée serait plus souple pour remplacer une stratégie selon la configuration, mais ajouterait
une configuration à maintenir. Les calculs de délai sont également sélectionnés par le mode,
sans `switch` dans l'estimateur.

## Preuve par l'extension

À l'étape 8, j'ai ajouté `TarifFluvial`, `Peniche` et `SupplementHorsGabarit`, ainsi que leurs tests.
J'ai modifié sept fichiers de production existants : `ModeTransport`, `Delais`, `Flotte`,
`Utils`, `AssemblagePrix`, `Application` et `Main`. Le diagnostic
initial recensait sept emplacements pour le mode et deux pour le supplément, avec un recouvrement
dans le calculateur. La comparaison montre que l'extension nécessite encore de modifier des fichiers
existants : le critère idéal « uniquement de nouvelles classes et l'assemblage » n'est pas atteint.

## Compatibilité du supplément

Le supplément automatique entre en contradiction avec les tests historiques : plusieurs de leurs
colis dépassent 200 cm, mais leurs prix attendus ne comprennent pas le supplément. Activer la règle
pour toutes les applications ferait échouer six scénarios de contrat. `Application()` conserve donc
le tarif historique, tandis que `Application(true)` active le supplément pour toute marchandise hors
gabarit. `Main` utilise la nouvelle règle ; l'adaptateur de contrat utilise la configuration historique.
La nouvelle règle s'applique après les autres suppléments et avant l'urgence, la remise et l'assurance.
Ce choix permet de tester les deux comportements, mais ne démontre pas que le nouveau tarif automatique
respecte les anciens prix. Pour l'imposer à tous les clients, les attentes du contrat doivent évoluer.

Le mode FLUVIAL est testé directement dans le module refactorisé, car le contrat partagé ne contient
pas cette valeur et les consignes interdisent de le modifier.
