# Youtube 앱 만들기

## Youtube 앱 개요
 - Retrofit을 이용하여 영상 목을을 받아와 구성한다.
 - MotionLayout을 이용하여 유튜브 영상 플레이어 화면전환 애니메이션을 구현한다.
 - 영상 목록을 클릭하여 ExoPlayer를 이용하여 영상을 재생할 수 잇다.

## 학습할 내용
 - **MotionLayout** 사용하기
 - **Exoplayer** 사용하기

### MotionLayout 사용 개요
 - ConstraintLayout 라이브러리의 일부(서브 클래스)
 - 레이아웃 전환과 UI 이동, 크기 조절 및 애니메이션에 사용

### ExoPlayer 사용 개요
 - Google이 Android SDK와 별도로 배포한 오픈소스 프로젝트
 - 오디오 및 동영상 재생 가능
 - 오디오 및 동영상 재생 관련 강력한 기능들 포함
 - 유튜브 앱에서 사용하는 라이브러리 

## MotionLayout
### 개요
<p>
 
**MotionLayout**은 앱에서 모션과 위젯 애니메이션을 관리하는 데 사용할 수 있는 레이아웃 유형이다. **MotionLayout**은 **ConstraintLayout**의 서브클래스이며 ConstraintLayout의 다양한 레이아웃 기능을 기초로 한다. **ConstraintLayout** 라이브러리의 일부인 **MotionLayout**은 지원 라이브러리로 사용가능하며, **API Level 14**와 호환된다.
</p>

<p align="center">
 
 <img src="https://developer.android.com/images/training/constraint-layout/motion-simple.gif?hl=ko"/>
 
 **그림.** 기본 터치 컨트롤 모션
 </p>
 
<p>
 
**MotionLayout**은 레이아웃 전환과 복잡한 모션 처리 사이를 연결하며 속성 애니메이션 프레임워크, TransitionManager 및 CoordinatorLayout 사이의 혼합된 기능을 제공한다.
</p>

<p>
 
 레이아웃 사이의 전환 외에도 **MotionLayout**을 사용하여 레이아웃 속성을 애니메이션으로 보여줄 수 있다. 또한 기본적으로 검색 가능 전환을 지원한다. 즉, 터치 입력과 같은 일부 조건에 따라 전환 내의 포인트를 즉시 표시할 수 있다.</br>
 **MotionLayout**에서는 키프레임도 지원하므로 사용자의 필요에 맞게 완전히 맞춤설정된 전환을 사용할 수 있다.
 </p>
 
 <p>
 
 **MotionLayout**은 완전히 선언 가능하므로, 복잡도에 상관없이 XML로 모든 전환을 설명할 수 있다.
 </p>
 
 <P>

** 참고: MotionLayout은 직접 하위 요소와만 호환된다! 중첩 레이아웃 계층 구조 또는 활동 전환은 지원하지 않는다! **
 </p>


