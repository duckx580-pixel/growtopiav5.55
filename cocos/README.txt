Cocos2d-x / Cocos Creator analysis
==================================

The resources/, sources/ and smali/ folders next to this one are the Android
shell of the game. This cocos/ folder is the game's own script code.
Nothing from the uploaded file was executed.

Script files found: 0 .jsc, 0 .luac, 1 .lua
Native libraries:   libanzu.so, libc++_shared.so, libcrashlytics-common.so, libcrashlytics-handler.so, libcrashlytics-trampoline.so, libcrashlytics.so, libdatastore_shared_counter.so, libgrowtopia.so

Key: not needed — this build ships its scripts unencrypted

JavaScript:  0 file(s) in js/
Lua source:  1 file(s) in lua/
Lua chunks kept as bytecode: 0 file(s) in bytecode/

Limitations
-----------
Comments and original formatting are not stored in compiled Lua chunks. Decrypted
JavaScript is build output, so it is bundled and minified the way the developer
shipped it. Scripts downloaded as a hot update after installation were never in the
uploaded file, and a game that derives its key at runtime rather than storing it
cannot be decrypted by a static tool.
