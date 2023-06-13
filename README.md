# 스마트폰 게임 프로그래밍 과제 - 도트 서바이버


## 개요
로그라이트 탑뷰 슈터 게임(통칭 뱀서라이크)

게임 화면 세로

조작은 화면 하단의 이동 패드 버튼만을 이용해서 움직이는 것이 전부

캐릭터의 공격은 자동으로 적 향해 발사

플레이어 주변 화면 밖에서 적들이 주기적으로 생성된다.

적들이 사방에서 플레이어를 향해 이동과 공격

적들을 처치할 때마다 경험치를 획득

경험치를 전부 채워 레벨업 할때마다 새 무기를 얻거나 기존의 무기를 업그레이드 가능

게임의 목표는 일정 시간까지 살아남는 것이 목표

비슷한 게임으로는 모티브가 되는 뱀파이어 서바이벌, 탕탕특공대 등이 있다.

---
## 구현 방식

### 무한맵
똑같은 맵 이미지를 4개 준비해서
플레이어가 왼쪽으로 이동하면 오른쪽에 있는 맵을 왼쪽으로 두칸 옮기는 식으로 맵이 무한한 크기인 것처럼 보이게 하였다.

### 몬스터
몬스터는 플레이어를 향해 움직이게 하였고
플레이어와 마찬가지로 여러 상태를 만들어서 이동, 공격당할때, 사망한 상태 등으로 나누었다.
collidercheck 클래스를 통해 몬스터와 플레이어가 부딪히면 플레이어가 공격받은 판정이 되게하였다.

### 그림자
플레이어나 몬스터를 생성할때 플레이어 위치에 그림자를 생성함
그림자는 타겟팅된 몬스터나 플레이어의 위치를 따라다님.
플레이어나 몬스터가 삭제될때 동시에 삭제하도록 하였음.

### 플레이어의 공격
원거리 기본 공격 파이어볼.
플레이어는 몬스터 레이어에 있는 몬스터들을 탐색
그 중에서 플레이어 일정 거리 내에 있는 사망하지 않은 가장 가까운 몬스터를 향해 주기적으로 파이어볼 생성
파이어볼은 적과 플레이어 사이의 각도를 계산해
적을 향하는 방향으로 이동됨.
파이어볼은 적과 부딪히거나 일정시간이 지나면 삭제됨.
collidercheck 클래스를 통해
파이어볼이 적과 부딪히면 몬스터가 공격받는 판정이 되게 하였습니다.
몬스터가 공격받으면 hurt상태가 되어서 잠깐 피격무적 상태가 되게 하였다.

calculate 라는 static 클래스를 만들어서 
두 점사이의 거리나 각도, 각도를 라디안으로의 변환을 하는 계산 등을 하였다.

### 타이틀 화면
수업시간에 배운 것 처럼 타이틀 화면은 레이아웃 기능을 이용해서 기본적인 타이틀화면을 만들었다.
타이틀화면에서 시작하면 바로 본게임으로 들어간다.타이틀 화면

### HP바
플레이어의 HP상태를 나타내는 HP바.
현재 HP/ 최대 HP 비율로 현재 HP에 따라 체력바가 표시되게 만들었다.

### 몬스터 스포너
플레이어가 보이지 않는 화면밖의 위치에서 주기적으로 몬스터를 생성할 수 있다.
좌우위아래 대각선까지 여러 방향에서 무작위로 몬스터가 소환된게 만들어 주위에서 몬스터가 오는 것 처럼 만들었다.

### 코인
몬스터가 사망할때 그 자리에 코인이 생성된다.
플레이어가 해당 코인을 먹으면 경험치가 상승한다.

### EXP바
HP바 처럼 최대 경험치대비 현재 경험치 비율에 따라 경험치바가 차오르게 만들었다.
EXP가 전부 차오르면 레벨업하고 다시 EXP가 0이 된다.

### 일시정지
해당 UI 버튼이 눌리면 PAUSESCENE이 생성되어 잠시 게임이 멈춘다.

다시 정지버튼을 누르면 PAUSE씬을 없애고 원래 MAIN씬으로 돌아온다

EXIT버튼을 누르면 MAIN액티비티를 끝내고 타이틀화면으로 돌아간다.

--

## 개발 일정
~~- 1주차: 캐릭터와 이동과 무한 맵 구현~~
~~- 2주차: 적과 자동으로 다가오는 ai 구현~~
~~- 3주차: 캐릭터의 자동공격 구현, 체력과 게임오버 구현~~
~~  4주차: 캐릭터의 경험치와 레벨업 구현~~
- 5주차: 다양한 종류의 무기와 업그레이드 구현
~~  7주차: 주기적으로 등장하는 다음단계의 적 구현, 오브젝트 ~~
~~  8주차: 게임 ui~~
- 9주차: 게임 시작부터 끝까지 완성

## 아쉬운점
못한 것들: 시간이 지나며 점점 강한 몬스터가 나오는것, 레벨업에 따라 무기를 강화하는 것.
보충해야 할 것들: 효과음의 경우 여러개가 겹치면 하나만 나오는 경우가 있다.
