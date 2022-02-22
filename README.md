# MyFastCampusStudy_Android
[FastCampus] 30개 프로젝트로 배우는 Android 앱 개발

## Architecture For Android Programming

### Architecture를 사용하면 좋은 점
- 일관적인 코드 작성을 통해 유지보수와 협업에 용이하다.
- 일관적인 코드 작성을 통해 생산성이 향상된다.
- 코드 규칙이 정해져 있고, Business Model에 따른 구조를 갖기 때문에 테스트에 용이하다.
- App 개발의 방향성을 잡아준다.

### App 개발 시 사용하는 일반적인 아키텍쳐
1. MVC: Model + View + Controler
2. MVP: Model + View(View Controller) + Presenter
3. MVVM: Model + View(View Contoller) + ViewModel
4. MVVM + DataBinding
5. MvRx, Flux, Ribs, etc...

### MVC: Model + View + Controller
App 개발 시 사용하는 일반적인 Architecture 중 하나이다.

### MVP: Model + View(View Controller) + Presenter
- **View**: Activity, Fragment로 View의 Callback을 받는다.
- **Presenter**: Business Login과 View를 제어한다. Model과 View를 관리하긴 하는데 Interface를 구현해 View를 관리한다는 특징이 있다.
![image](https://user-images.githubusercontent.com/40654227/154975205-01cb494e-8a9a-49a9-9a20-669196425988.png)

### MVVM: Model + View(View Contrller) + ViewModel
View와 ViewModel이 구분된다는 특징이 있다.
View는 ViewModel을 알고 있지만, ViewModel은 View를 모르는 형태로 되어있다. 
그러기 때문에 단방향 에어가 가능하다.
ViewModel에서는 View의 데이터를 받아서 제어를 하고, 그 Model의 Data가 변형이 되었을 때, ViewModel에서는 그 변형된 Data를 Broadcasting 한다.
그렇게 해서 전파되었을 때, View에서는 Observing하여 Observer Pattern을 사용하여 ViewModel에서 직접 구독을 할 수 있는 Data Holder Class를 구독하거나 Observer Pattern을 통한 Callback을 통해서 Data가 변경되었을 때 View가 변형이 될 수 있도록 제공한다.
![image](https://user-images.githubusercontent.com/40654227/155152971-e70dff14-0226-4f3d-a314-064ae7431ed5.png)
