# SauceDemo UI Test Cases

`https://www.saucedemo.com/`에서 도달 가능한 화면 전체(로그인 → 메인 → 상품상세 → 장바구니 → 체크아웃)를 대상으로 작성한 Cucumber 시나리오 목록이다. 총 29개 시나리오, 5개 feature 파일로 구성된다.

각 시나리오는 `mvn test -Dcucumber.filter.tags="@TestCase-ID"` 형식으로 단독 실행할 수 있고, feature 태그(`@Login`, `@MainPage`, `@ProductDetail`, `@Cart`, `@Checkout`)로 파일 단위 실행도 가능하다.

## login.feature (`@Login`) — 7개

| 태그 | 시나리오 | 확인 내용 |
|---|---|---|
| `@TestCase-Login-01` | Successful login with standard user | `standard_user`/`secret_sauce` 정상 로그인 → inventory 페이지 이동 |
| `@TestCase-Login-02` | Fail login (username/password 불일치) | 잘못된 비밀번호 → "Username and password do not match" 에러 |
| `@TestCase-Login-03` | Fail login with locked out user | `locked_out_user` → "Sorry, this user has been locked out." 에러 |
| `@TestCase-Login-04` | Login with problem user shows broken product images | `problem_user`는 로그인은 성공하지만 상품 이미지 6개가 전부 동일한 깨진 이미지로 표시됨 |
| `@TestCase-Login-05` | Fail login with empty username | username 빈 값 → "Username is required" 에러 |
| `@TestCase-Login-06` | Fail login with empty password | password 빈 값 → "Password is required" 에러 |
| `@TestCase-Login-07` | Login with error user cannot remove item from cart | `error_user`로 로그인 후 장바구니에서 아이템을 "제거"해도 실제로는 배지 카운트가 줄지 않는 사이트 자체의 버그 재현 |

## MainPage.feature (`@MainPage`) — 8개

| 태그 | 시나리오 | 확인 내용 |
|---|---|---|
| `@TestCase-Main-01` | the user can see sidebar | 사이드바 열기/닫기 |
| `@TestCase-Main-02` | the user can see about page | About 링크 클릭 시 `saucelabs.com`으로 이동 |
| `@TestCase-Main-03` | sort by price low to high | 가격 오름차순 정렬 |
| `@TestCase-Main-04` | sort by price high to low | 가격 내림차순 정렬 |
| `@TestCase-Main-05` | sort by name Z to A | 이름 역순 정렬 |
| `@TestCase-Main-06` | add and remove a product from the cart | 상품 담기/빼기 시 장바구니 배지 변화 |
| `@TestCase-Main-07` | Reset App State clears the cart badge | Reset App State 클릭 시 배지가 사라짐 |
| `@TestCase-Main-08` | the user can logout | 로그아웃 후 로그인 페이지로 복귀 |

## ProductDetail.feature (`@ProductDetail`) — 3개

| 태그 | 시나리오 | 확인 내용 |
|---|---|---|
| `@TestCase-ProductDetail-01` | view product detail page | 상품명 클릭 시 상세 페이지에 올바른 이름 표시 |
| `@TestCase-ProductDetail-02` | go back to products from detail page | Back to products 버튼으로 메인 페이지 복귀 |
| `@TestCase-ProductDetail-03` | add product to cart from detail page | 상세 페이지에서 장바구니 담기 → 버튼이 "Remove"로 바뀌고 배지 반영 |

## Cart.feature (`@Cart`) — 4개

| 태그 | 시나리오 | 확인 내용 |
|---|---|---|
| `@TestCase-Cart-01` | view items added to the cart | 담은 상품 2개가 장바구니 페이지에 표시됨 |
| `@TestCase-Cart-02` | remove an item from the cart page | 장바구니 페이지에서 삭제 시 목록/배지 반영 |
| `@TestCase-Cart-03` | continue shopping from the cart page | Continue Shopping 버튼으로 메인 페이지 복귀 |
| `@TestCase-Cart-04` | proceed to checkout from the cart page | Checkout 버튼 클릭 시 결제 정보 입력 페이지로 이동 |

## Checkout.feature (`@Checkout`) — 7개

| 태그 | 시나리오 | 확인 내용 |
|---|---|---|
| `@TestCase-Checkout-01` | complete checkout with valid information | 정상 결제 완료 (합계 금액 검증 포함) 및 완료 후 장바구니 초기화 |
| `@TestCase-Checkout-02` | requires first name | First Name 미입력 시 에러 |
| `@TestCase-Checkout-03` | requires last name | Last Name 미입력 시 에러 |
| `@TestCase-Checkout-04` | requires postal code | Postal Code 미입력 시 에러 |
| `@TestCase-Checkout-05` | cancel checkout information step | 정보 입력 단계 취소 → 장바구니 페이지로 복귀 |
| `@TestCase-Checkout-06` | cancel checkout overview step | 주문 개요 단계 취소 → 메인 페이지로 복귀 |
| `@TestCase-Checkout-07` | complete checkout with an empty cart | 빈 장바구니로도 결제 진행 가능, 합계 `$0.00` |

## 탐색 중 발견한 사이트/코드 버그

실제 사이트를 브라우저로 탐색하며 기존 코드에 있던 버그를 확인하고 수정했다.

- `locked_out_user` 시나리오가 틀린 비밀번호를 사용하고 있어 실제로는 lockout 메시지가 아닌 "일치하지 않음" 에러가 발생하는 상태였음 → 올바른 비밀번호로 수정
- About 링크 도메인 검증이 `sourcelabs.com`으로 오타나 있었음 → `saucelabs.com`으로 수정
- 사이드바 닫기 버튼 로케이터가 실제로는 부모 엘리먼트에만 있는 클래스(`bm-cross-button`)를 찾고 있어 항상 실패하던 상태였음 → id 기반 로케이터로 수정
- react-burger-menu 라이브러리의 사이드바 링크(닫기/About/Reset/Logout)는 Selenium의 네이티브 마우스 클릭에 반응하지 않고 JS `element.click()`에만 반응함 → JS 클릭으로 우회
- `error_user`는 인벤토리 페이지에서 "Remove" 버튼을 눌러도 실제로 장바구니에서 빠지지 않는 사이트 자체 버그 확인 (`@TestCase-Login-07`로 재현 고정)
