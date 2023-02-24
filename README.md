# moo

moo is - or rather will be - a daily mood tracker.

## Some notes and ideas

- ask user for display name upon registering and save it to shared preferences
- display name is then shown in the app -> Hello, (name)!
- card history utilises some kind of RecyclerView with cards as items

### DB-Schema:

*Card-Objects*

| attribute | data type     | vals                   |
|-----------|---------------|------------------------|
| date      | LocalDateTime |                        |
| mood      | String        | happy, neutral unhappy |
| highlight | String        |                        |
| notes     | String        |                        |