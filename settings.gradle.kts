rootProject.name = "DLanguage"

include (
    ":dlang:plugin-api",
    ":dlang:plugin-impl",
    "utils",
    "errorreporting",
    "debugger",
    "sdlang",
    "dub"
)
