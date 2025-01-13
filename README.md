## ⚙️ Android Studio version
- Koala 사용
- targetSDK : 34
- minSDK : 24
- Emulator & Device 활용

## 📁 Library
- BottomNavigationView : 하단바 연결 시 활용

## 📌 Convention
### Branch Naming Convention
- main
- develop
- feature/Issue#-feature
- hotfix/Issue#-feature
- refactor/Issue#-feature

### Issue Convention
- 담당자를 명시합니다.
[예시]
  [#1] 하단바 Fragment 연결
    TODO
      [ ] BottomNavigationView 작성하기
      [ ] MainActivity, Fragment에 연결하기
 
### 🎯 Commit Convention
| 태그이름   | 내용                                                                      |
| ---------- | ------------------------------------------------------------------------- |
| `add`     | 새로운 파일 추가                              |
| `feat`     | 만들어진 파일에 새로운 기능 추가                                                          |
| `fix`   | 기능 수정, 버그 수정                                                       |
| `docs`  | 문서 수정                                    |
| `comment`    | 주석 추가                     |
| `test` | 테스트 코드, 리팩토링 테스트 코드 추가                                                    |
| `merge`  | 다른 브랜치와의 merge                                                  |
| `refactor`     | 코드 수정 및 타입, 변수명 변경                                                        |
| `style`     | 코드 스타일 변경(코드 자체의 변경 없이 스타일만 변경된 경우)                        |
| `remove`    | 코드 또는 파일, 리소스 제거 |
| `setting`   | 패키지 구조 변경                        |


## 📍 Co-Work Flow
1. Issue를 생성합니다.
2. Issue 번호로 시작하는 브랜치를 판 후 개발을 시작합니다.
3. 개발 완료 후 Pull Request를 생성합니다.
4. develop 브랜치에 Merge를 진행합니다.

