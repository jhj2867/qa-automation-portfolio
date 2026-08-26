---
name: saucedemo-test-harness
description: SauceDemo(https://www.saucedemo.com/) 진입 URL을 받아 거기서 도달 가능한 사이트 전체를 브라우저로 탐색하고, QA 관점에서 있어야 할 Cucumber 테스트 케이스를 최대한 폭넓게 생성하고, 없는 step definition/page object를 구현한 뒤 테스트를 실행해 결과를 보여준다. 중간에 범위/방식을 확인하는 질문 없이 끝까지 진행한다. ui-test-automation-saucedemo 프로젝트에 테스트 케이스를 만들어달라는 요청("이 페이지 테스트 만들어줘", "케이스 뽑아줘", "하네스로 돌려줘" 등)에 사용.
---

# SauceDemo 테스트 하네스

사용자가 SauceDemo 진입 URL을 주면, 거기서 도달 가능한 사이트 전체를 대상으로 QA 관점에서
있어야 할 Cucumber 테스트 케이스를 최대한 폭넓게 생성하고, 필요한 코드를 구현하고, 테스트를
실행해 결과를 보고한다. 대상 프로젝트는 `ui-test-automation-saucedemo/` (JUnit5 + Cucumber +
Selenium, base URL `https://www.saucedemo.com/`).

**핵심 원칙: 최대 생성 후 사용자가 가지치기.** 케이스 개수를 스스로 줄이지 않는다. "이 정도면
충분하다"고 판단해서 탐색이나 케이스 생성을 조기에 멈추지 않는다. 불필요한 케이스는 사용자가
보고 나서 직접 지울 것이므로, 애매하면 만든다.

**중간에 묻지 않는다.** 범위, 계정 선택, 스타일, 케이스 개수, 진행 방식 등에 대해
AskUserQuestion으로 확인받지 않고 아래 기본값으로 바로 진행해서 결과물까지 만든 뒤 한 번에
보고한다. 사용자가 준 URL이 없을 때만 물어본다. (git push, 브랜치 삭제 등 시스템 차원의 위험한
작업에 대한 확인 절차는 이 스킬과 무관하게 항상 별도로 적용된다.)

## 0. 입력 확인
- URL이 없으면 그것만 물어본다. 그 외에는 묻지 않고 아래 기본값으로 진행한다.
- 로그인 테스트는 기본적으로 로그인 페이지 하단에 공개된 모든 데모 계정
  (`standard_user`, `locked_out_user`, `problem_user`, `performance_glitch_user`,
  `error_user`, `visual_user`, 비밀번호는 전부 `secret_sauce`)을 대상으로 시도한다 — 계정마다
  동작이 다르므로 이것 자체가 케이스 소스다.
- base URL은 `src/test/resources/config.properties`에 있다.

## 1. 범위: "페이지 하나"가 아니라 "도달 가능한 사이트 전체"
사용자가 준 URL은 탐색의 시작점일 뿐, 대상 범위가 아니다. 그 URL에서 로그인/클릭/이동으로
도달 가능한 모든 화면을 대상으로 삼는다. SauceDemo 기준으로는 최소한 아래를 전부 포함:
- 로그인 페이지 (모든 데모 계정 × 유효/무효 자격증명, 빈 입력 등 폼 검증)
- 메인/인벤토리 페이지 (정렬, 사이드바 메뉴, 장바구니 담기/빼기, 상품 상세 진입)
- 상품 상세 페이지
- 장바구니 페이지
- 체크아웃 플로우 (정보 입력, 개요, 완료, 각 단계의 유효성 검증)
- 로그아웃 / Reset App State
- 사이드바의 외부 링크(About 등)

이미 있는 `.feature` 파일이 커버하는 화면이라도, 다시 탐색해서 빠진 케이스가 있으면 채운다.
"이미 몇 개 있으니 됐다"고 넘어가지 않는다.

## 2. 기존 컨벤션 파악
새 코드를 짜기 전에 반드시 아래를 다시 읽어서 스타일을 맞춘다 (파일이 바뀌었을 수 있으므로 매번
다시 읽을 것, 아래 요약을 맹신하지 말 것):
- `src/test/resources/features/*.feature` — 기존 시나리오 스타일, 태그 규칙
- `src/test/java/stepdefinitions/*.java` — 기존 step 문구 전체 (재사용 및 충돌 방지)
- `src/test/java/pages/*.java` — 기존 page object 스타일

컨벤션 요약 (참고용, 실제 파일로 재확인할 것):
- Feature 파일: 페이지당 1개, 파일 상단에 `@PageName` 태그, `Feature: SauceDemo <Page> page`.
- 로그인이 필요한 페이지는 `Background: the user login` → `Given the user login` 사용
  (LoginPage를 거쳐 로그인하는 공용 스텝, `MainPageSteps`에 정의되어 있음).
- 시나리오 태그: `@TestCase-<Page>-NN` (NN은 01부터 zero-padded 2자리, 페이지 내 순번).
- Page object: `pages/<Page>Page.java`. `By` 상수 또는 `@FindBy` 필드 + 동작 메서드. 생성자는
  `WebDriver driver`를 받는다.
- Step definition: `stepdefinitions/<Page>Steps.java`. `org.junit.jupiter.api.Assertions`
  사용, `DriverFactory.getDriver()`로 드라이버 접근.
- 새 스텝 문구를 추가하기 전, 기존 스텝 정의들과 문구가 겹치거나 애매(ambiguous)하지 않은지
  확인한다 (Cucumber는 전역으로 step을 매칭한다).

## 3. 탐색
1. Chrome 브라우저 도구가 로드되어 있지 않으면 `ToolSearch`로
   `mcp__claude-in-chrome__tabs_context_mcp, navigate, computer, read_page, get_page_text, find, tabs_create_mcp, tabs_close_mcp, javascript_tool`를
   로드한다.
2. 새 탭에서 진입 URL로 이동해 도달 가능한 화면들을 전부 순회한다. 로그인이 필요하면 계정별로
   로그인해서 차이를 관찰한다.
3. 각 화면의 인터랙티브 요소(버튼, 링크, 폼, 정렬/필터, 장바구니 등)를
   `read_page`/`get_page_text`/`find`로 파악한다.
4. 실제로 클릭/입력해보며 정상 케이스와 예외/음성 케이스(빈 입력, 잘못된 값, 잠긴 계정, 깨진
   이미지 등 SauceDemo 특유의 케이스)를 관찰한다. 화면에 나타나는 정확한 에러 메시지 문구를
   그대로 기록해둔다 (assertion에 그대로 사용해야 함). 추정하지 말고 실제로 확인한다 —
   기존 코드/주석에 적힌 예상 동작도 실제 사이트로 재검증한다 (틀려도 그대로 옮기지 않는다).
5. `computer` 툴의 클릭/타이핑이 반응하지 않으면(탭이 백그라운드로 밀려 `document.hasFocus()`나
   `visibilityState`가 이상한 경우) 새 탭을 만들어 재시도하고, 그래도 안 되면
   `javascript_tool`로 React 컨트롤드 인풋에 네이티브 setter + `input` 이벤트를 직접 디스패치해서
   우회한다:
   ```js
   function setNativeValue(el, value) {
     const proto = Object.getPrototypeOf(el);
     const setter = Object.getOwnPropertyDescriptor(proto, 'value').set;
     setter.call(el, value);
     el.dispatchEvent(new Event('input', { bubbles: true }));
   }
   ```
   이 우회는 탐색(정보 수집) 단계에서만 쓰고, 실제 테스트 코드는 항상 Selenium의
   `sendKeys`/`click`으로 짠다 (실사용자 입력 흉내가 목적이므로).
6. 링크가 외부 도메인으로 열리면(About 링크처럼 새 탭으로 열리는 경우 등) 그 동작 방식도 확인한다.
7. alert/confirm 같은 브라우저 다이얼로그를 띄우는 요소는 클릭하지 않는다 (세션이 멈춘다).
8. 다 쓴 탭은 정리한다.

## 4. 테스트 케이스 설계
- 탐색한 화면마다, QA로서 있어야 할 케이스를 최대한 뽑는다: 정상 플로우, 계정별 차이, 입력 검증
  (빈 값/특수문자/경계값), 상태 변화(장바구니 개수, 정렬 결과), 네비게이션, 이미 발견한 사이트
  버그(problem_user의 깨진 이미지 같은 것) 등. 스스로 개수를 줄이지 않는다 — 많으면 사용자가
  나중에 지운다.
- 같은 페이지의 시나리오가 이미 `.feature` 파일에 있으면 그 파일에 이어서 추가하고, 새 페이지면
  새 `.feature` 파일을 만든다.
- Given/When/Then Gherkin으로 작성하고, 기존에 이미 존재하는 step 문구를 최대한 재사용한다.
- 기존 시나리오 중 실제 사이트 동작과 맞지 않는 게 발견되면(자격증명 오타, 잘못된 기대값 등)
  묻지 말고 바로 고친다.

## 5. 구현
- feature 파일 작성/수정
- 없는 step definition 구현 (기존 클래스에 이어 붙이거나, 새 페이지면 새 클래스 생성)
- 없는 page object 로케이터/메서드 구현
- `TEST_CASES.md`(프로젝트 루트)에 추가/변경된 시나리오를 반영한다. 태그, 시나리오 이름, 확인
  내용을 표로 정리하고, 탐색 중 발견한 사이트/코드 버그도 별도 섹션에 남긴다. 파일이 없으면
  새로 만든다.

## 6. 실행 및 결과 보고
**테스트 실행은 항상 `runners.TestRunner`(JUnit Suite)를 통해서만 한다.** 표준 명령은:

```
mvn test -Dtest=TestRunner
```

(태그로 좁혀서 개발 중 빠른 피드백을 볼 땐 `-Dcucumber.filter.tags="@<PageTag>"`를 같이 붙인다:
`mvn test -Dtest=TestRunner -Dcucumber.filter.tags="@<PageTag>"`.) `mvn`이 PATH에 없으면 IDE
번들 maven(예: IntelliJ의 `.../plugins/maven/lib/maven3/bin/mvn`)을 `sh`로 실행한다.

디버깅용으로 별도 스크래치 JUnit 클래스(`src/test/java/debug/` 등)를 만들었더라도, 그건 원인
조사 전용이지 "테스트 실행"이 아니다 — 결과 검증은 항상 TestRunner를 통해서만 하고, 스크래치
클래스는 최종 검증 전에 반드시 삭제한다. 결과물에 디버그 코드가 남으면 안 된다.

1. 실패가 있으면 원인을 분석해서(로케이터 오류, 사이트 실동작과의 불일치, step 문구 충돌 등)
   바로 고치고 재실행한다 — 승인을 구하지 않는다.
2. **최종 검증은 태그 필터 없이 `mvn test -Dtest=TestRunner` 한 번으로 전체 스위트를 전부
   통과시켜서 확인한다.** 이 프로젝트에서 정답 소스는 이 JUnit(Cucumber) 실행 결과 하나뿐이다 —
   브라우저 도구로 "동작하는 것 같다"를 확인한 건 탐색/디버깅 참고용일 뿐, 최종 판단 기준이
   아니다. 태그별로 나눠 돌리는 것으로 검증을 끝내지 않는다. 내가 건드리지 않은 기존 시나리오가
   이 최종 실행에서 실패하면(사전부터 깨져 있던 것) 원인을 파악해서 보고에 포함하되, 범위 밖이면
   고칠지 여부만 물어본다.
3. **실행이 끝나면 항상 리포트를 확인한다.** 두 가지가 있다:
   - Cucumber 내장 html 리포트: `TestRunner`에 `html:target/cucumber-reports/report.html`로
     설정되어 있어 `mvn test -Dtest=TestRunner`를 돌릴 때마다 갱신된다.
     `open ui-test-automation-saucedemo/target/cucumber-reports/report.html`(macOS)로 바로
     연다. 별도 툴 설치 없이 파일을 그냥 열면 되는 게 장점.
   - Allure 리포트: `pom.xml`에 `allure-maven` 플러그인이 있어 Allure CLI를 따로 설치할 필요
     없이 `mvn allure:serve`(로컬 서버를 띄워서 바로 열람, `Ctrl+C`로 종료) 또는
     `mvn allure:report`(정적 생성, 결과물은 `target/site/allure-maven-plugin/index.html`이지만
     `file://`로 직접 열면 데이터 fetch가 CORS에 막히므로 `allure:serve`를 쓰거나 별도로
     서빙해야 함)로 본다. GitHub Pages에 배포되는 trend history 포함 버전은 CI에서만 만들어지고,
     로컬 `allure:serve`는 그 실행 시점의 결과만 보여준다(과거 이력 없음).

   `hooks/Hooks.java`의 `@After`가 시나리오마다 끝나는 시점에 스크린샷을 찍어 `scenario.attach()`로
   붙여두므로, 두 리포트 모두에서 시나리오별 최종 화면을 그대로 볼 수 있다.
4. 사용자에게 한 번에 요약해서 보여준다: 생성/수정된 파일 목록, 전체 시나리오 개수와 그중 신규로
   만든 것/기존 걸 고친 것 구분, 최종 `mvn test -Dtest=TestRunner`(전체) 실행 결과(통과/실패
   개수), HTML 리포트 경로, 발견한 기존 버그.

## 주의사항
- 실제 로그인 자격 증명이나 민감 정보를 하드코딩하지 않는다 (SauceDemo는 공개 데모 계정만 사용).
- git commit/push은 사용자가 명시적으로 요청할 때만 한다.