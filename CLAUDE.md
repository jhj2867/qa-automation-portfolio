# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Repository purpose

This is Hyun Jun Jo's personal QA portfolio repository. It has two distinct parts that are unrelated to each other and should be treated independently:

1. **`index.html`** — a static, single-page portfolio site (no build tooling, no framework) published via GitHub Pages at https://jhj2867.github.io/qa-automation-portfolio/. It's plain HTML/CSS/JS in one file with a light/dark toggle and an EN/KR language toggle (`toggleLang()`). Edit it directly; there is no bundler or dev server — open it in a browser to preview.
2. **Sample QA automation projects** — independent Maven modules (not a multi-module parent build) demonstrating test automation skills:
   - `api-test-automation-framework/` — REST API testing with Rest Assured + TestNG.
   - `ui-test-automation-saucedemo/` — Selenium + Cucumber UI automation, run via the Cucumber JUnit Platform Engine (JUnit 5).

`project.md` and `README.md` describe these projects/skills in prose (README.md also doubles as the source content for the portfolio page's resume section).

## Commands

Each Maven project is standalone; run Maven commands from inside that project's directory (there is no root `pom.xml`).

```bash
# From api-test-automation-framework/
mvn test                                   # run all tests
mvn test -Dtest=UserApiTest                # run a single test class
mvn test -Dtest=UserApiTest#getUserTest    # run a single test method
```

```bash
# From ui-test-automation-saucedemo/
mvn test                                   # runs runners.TestRunner, which executes all Cucumber features
```

Both modules target Java 25 (`maven.compiler.source/target`).

## Architecture notes

- `api-test-automation-framework` uses TestNG (not JUnit) with Rest Assured's `given()/when()/then()` fluent style; tests live under `src/test/java/tests`. There is no main/production source — this is a test-only framework project.
- `ui-test-automation-saucedemo` uses Cucumber + JUnit 5 (via `cucumber-junit-platform-engine` and `junit-platform-suite`, not TestNG): `runners/TestRunner.java` is a `@Suite` class that discovers `.feature` files under `src/test/resources/features`, with glue code in `stepdefinitions/` and `hooks/`. Page objects live under `pages/` (Selenium `PageFactory`); `utils/DriverFactory` manages the thread-local WebDriver and `utils/ConfigReader` reads `src/test/resources/config.properties`. Assertions use `org.junit.jupiter.api.Assertions`.
- Test reports come from the `maven-surefire-plugin`; there's no separate reporting library configured.