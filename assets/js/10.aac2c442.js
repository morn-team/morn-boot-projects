(window.webpackJsonp=window.webpackJsonp||[]).push([[10],{398:function(a,e,t){"use strict";t.r(e);var n=t(52),s=Object(n.a)({},(function(){var a=this,e=a.$createElement,t=a._self._c||e;return t("ContentSlotsDistributor",{attrs:{"slot-key":a.$parent.slotKey}},[t("h1",{attrs:{id:"beanenhance-实例增强"}},[t("a",{staticClass:"header-anchor",attrs:{href:"#beanenhance-实例增强"}},[a._v("#")]),a._v(" BeanEnhance 实例增强")]),a._v(" "),t("p",[a._v("MornBoot提供更多的注解对实例进行描述，使用"),t("code",[a._v("BeanCaches")]),a._v("实例缓存"),t("strong",[a._v("批量检索")]),a._v("需要的实例。在"),t("code",[a._v("1.2.0+")]),a._v("版本中，实例增强还支持对方法的缓存、检索和调用。")]),a._v(" "),t("blockquote",[t("p",[a._v("Since：v1.0.0")])]),a._v(" "),t("h3",{attrs:{id:"functions"}},[t("a",{staticClass:"header-anchor",attrs:{href:"#functions"}},[a._v("#")]),a._v(" Functions")]),a._v(" "),t("ul",[t("li",[a._v("实例缓存、检索、事件回调")]),a._v(" "),t("li",[a._v("方法缓存、检索、调用")]),a._v(" "),t("li",[a._v("快速自定义注解")])]),a._v(" "),t("h3",{attrs:{id:"实例注解"}},[t("a",{staticClass:"header-anchor",attrs:{href:"#实例注解"}},[a._v("#")]),a._v(" "),t("a",{attrs:{name:"实例注解"}},[a._v("实例注解")])]),a._v(" "),t("p",[a._v("使用"),t("code",[a._v("@Name")]),a._v("、"),t("code",[a._v("@Tag")]),a._v("、"),t("code",[a._v("@Target")]),a._v("为实例设置名称、标签、目标，以便在需要的时候检索这些实例。")]),a._v(" "),t("h4",{attrs:{id:"示例"}},[t("a",{staticClass:"header-anchor",attrs:{href:"#示例"}},[a._v("#")]),a._v(" 示例")]),a._v(" "),t("div",{staticClass:"language- extra-class"},[t("pre",{pre:!0,attrs:{class:"language-text"}},[t("code",[a._v('@Slf4j\n@Component\n@Name("caramel") // 为实例命名\n@Tag("small") // 标签，描述实例的特征\n@Target(Food.class) // 目标，描述实例的作用目标\npublic class Caramel implements Pet {\n\n  @Override\n  public String eat(Food food) {\n    log.info("{}在吃{}...", "caramel", food.getName());\n    return "fishBone";\n  }\n}\n')])])]),t("h3",{attrs:{id:"实例缓存"}},[t("a",{staticClass:"header-anchor",attrs:{href:"#实例缓存"}},[a._v("#")]),a._v(" 实例缓存")]),a._v(" "),t("p",[a._v("实例增强设计的使用场景是频繁的检索有共性的Bean。为了避免检索耗费时间，检索操作默认使用"),t("code",[a._v("BeanCaches")]),a._v("完成。")]),a._v(" "),t("blockquote",[t("p",[t("code",[a._v("BeanCaches")]),a._v("依赖SpringBoot的缓存机制，因此必须开启缓存注解"),t("code",[a._v("@EnableCaching")]),a._v("。")])]),a._v(" "),t("h4",{attrs:{id:"示例-2"}},[t("a",{staticClass:"header-anchor",attrs:{href:"#示例-2"}},[a._v("#")]),a._v(" 示例")]),a._v(" "),t("div",{staticClass:"language- extra-class"},[t("pre",{pre:!0,attrs:{class:"language-text"}},[t("code",[a._v('// 获取名为`caramel`的宠物\nPet caramel = BeanCaches.nameBean(Pet.class, "caramel");\n\n// 获取标签为`small`的宠物\n// 注意：此例中，BeanCaches会尝试检索所有标签为`small`的实例，而不限于`Pet`的子类\nPet small = (Pet) BeanCaches.tagBean(null, "small"); \n\n// 获取所有目标为`Food`的宠物\nList<Pet> foods = BeanCaches.targetBeans(Pet.class, Food.class);\n\ncaramel.eat(new Food("fish")); // log：caramel在吃fish...\n')])])]),t("h4",{attrs:{id:"示例-观察者模式"}},[t("a",{staticClass:"header-anchor",attrs:{href:"#示例-观察者模式"}},[a._v("#")]),a._v(" 示例：观察者模式")]),a._v(" "),t("p",[a._v("基于"),t("code",[a._v("BeanCaches")]),a._v("编写观察者模式极为简洁，你甚至不需要在被观察者中，维护观察者的列表。因为"),t("code",[a._v("BeanCaches")]),a._v("会配合Spring容器检索所有的观察者实例。利用"),t("code",[a._v("@Name")]),a._v("、"),t("code",[a._v("@Tag")]),a._v("等注解，你还可以更为灵活的指定特定的观察者。")]),a._v(" "),t("div",{staticClass:"language- extra-class"},[t("pre",{pre:!0,attrs:{class:"language-text"}},[t("code",[a._v("// 被观察者\npublic class Subject {\n\n  /**\n   * 状态\n   */\n  private int state;\n\n  public int getState() {\n    return state;\n  }\n\n  public void setState(int state) {\n    this.state = state;\n    notifyObservers();\n  }\n\n  /**\n   * 通知全部观察者\n   */\n  private void notifyObservers() {\n    List<Observer> observers = BeanCaches.targetBeans(Observer.class, Subject.class);\n    for (Observer observer : observers) {\n      observer.update(state);\n    }\n  }\n}\n")])])]),t("div",{staticClass:"language- extra-class"},[t("pre",{pre:!0,attrs:{class:"language-text"}},[t("code",[a._v("// 观察者\npublic interface Observer {\n\n  /**\n   * 更新\n   *\n   * @param state 被观察者状态\n   */\n  void update(int state);\n}\n")])])]),t("div",{staticClass:"language- extra-class"},[t("pre",{pre:!0,attrs:{class:"language-text"}},[t("code",[a._v('// 状态观察者\n@Slf4j\n@Component\n@Target(Subject.class)\npublic class StateObserver implements Observer {\n\n  @Override\n  public void update(int state) {\n    log.info("Subject\'s state is {}", state);\n  }\n}\n')])])]),t("h4",{attrs:{id:"示例-beanprocessor"}},[t("a",{staticClass:"header-anchor",attrs:{href:"#示例-beanprocessor"}},[a._v("#")]),a._v(" 示例：BeanProcessor")]),a._v(" "),t("p",[a._v("基于MornBoot内置的函数式接口，我们可以将代码进一步简化。")]),a._v(" "),t("div",{staticClass:"language- extra-class"},[t("pre",{pre:!0,attrs:{class:"language-text"}},[t("code",[a._v("// 被观察者\npublic class Subject {\n\n  /**\n   * 状态\n   */\n  private int state;\n\n  public int getState() {\n    return state;\n  }\n\n  public void setState(int state) {\n    this.state = state;\n    notifyObservers();\n  }\n\n  /**\n   * 通知全部观察者\n   */\n  private void notifyObservers() {\n    BeanFunctionUtils.processes(StateProcessor.class, this);\n  }\n}\n")])])]),t("div",{staticClass:"language- extra-class"},[t("pre",{pre:!0,attrs:{class:"language-text"}},[t("code",[a._v('// 状态观察者\n@Slf4j\n@Component\n@Tag\npublic class StateObserver implements BeanProcessor<Subject> {\n\n  @Override\n  public void handle(Subject source) {\n    log.info("Subject\'s state is {}", source.getState());\n  }\n}\n')])])]),t("h4",{attrs:{id:"函数式接口"}},[t("a",{staticClass:"header-anchor",attrs:{href:"#函数式接口"}},[a._v("#")]),a._v(" 函数式接口")]),a._v(" "),t("p",[a._v("MornBoot提供多种函数式接口，同时也支持JDK内置的函数式接口，如："),t("code",[a._v("Consumer")]),a._v("、"),t("code",[a._v("Runable")]),a._v("等。")]),a._v(" "),t("ul",[t("li",[a._v("BeanAdapter")]),a._v(" "),t("li",[a._v("BeanConverter")]),a._v(" "),t("li",[a._v("BeanProcessor")]),a._v(" "),t("li",[a._v("BeanProducer")])]),a._v(" "),t("h4",{attrs:{id:"示例-混合检索"}},[t("a",{staticClass:"header-anchor",attrs:{href:"#示例-混合检索"}},[a._v("#")]),a._v(" 示例：混合检索")]),a._v(" "),t("p",[t("code",[a._v("BeanCaches")]),a._v("支持同时按多种条件检索Bean。")]),a._v(" "),t("div",{staticClass:"language- extra-class"},[t("pre",{pre:!0,attrs:{class:"language-text"}},[t("code",[a._v('// 构建检索条件，按标签和目标检索\nString[] tags = Tags.from(Color.class, "yellow").add("big").toArray();\nAnnotationIdentifyCase identifyCase = AnnotationIdentifyCase.builder()\n    .tags(tags).target(Toy.class).build();\nDog dog = BeanCaches.bean(Dog.class, identifyCase);\n')])])]),t("h3",{attrs:{id:"方法增强"}},[t("a",{staticClass:"header-anchor",attrs:{href:"#方法增强"}},[a._v("#")]),a._v(" "),t("a",{attrs:{name:"方法增强"}},[a._v("方法增强")])]),a._v(" "),t("p",[a._v("类似于实例增强，我们可以使用"),t("code",[a._v("@Function")]),a._v("注解为方法命名，以便在需要的时候调用它。")]),a._v(" "),t("blockquote",[t("p",[t("code",[a._v("@Function")]),a._v("默认使用方法名作为调用名称。")])]),a._v(" "),t("h4",{attrs:{id:"方法描述"}},[t("a",{staticClass:"header-anchor",attrs:{href:"#方法描述"}},[a._v("#")]),a._v(" 方法描述")]),a._v(" "),t("div",{staticClass:"language- extra-class"},[t("pre",{pre:!0,attrs:{class:"language-text"}},[t("code",[a._v('@Slf4j\n@Component\n@Tag\npublic class Dog {\n\n  @Function\n  public String eat(String food) {\n    log.info("狗在吃{}...", food);\n    return "bone";\n  }\n\n  @Function\n  public void play() {\n    log.info("狗在玩...");\n  }\n}\n')])])]),t("h4",{attrs:{id:"方法调用"}},[t("a",{staticClass:"header-anchor",attrs:{href:"#方法调用"}},[a._v("#")]),a._v(" 方法调用")]),a._v(" "),t("p",[a._v("方法增强适用于调用多个类中的相似方法，而且支持不同类、不同参数、不同返回值的多个方法，"),t("code",[a._v("BeanFunctions")]),a._v("会尽量调整参数传入顺序、数目，以便正确调用这些方法。")]),a._v(" "),t("div",{staticClass:"language- extra-class"},[t("pre",{pre:!0,attrs:{class:"language-text"}},[t("code",[a._v('// 构建检索条件，检索名为`eat`的方法\nAnnotationIdentifyCase functionId = AnnotationIdentifyCase.builder().name("eat").build();\nList<FunctionHolder> functions = BeanCaches.functions(functionId);\n// 调用`eat`方法\nList<String> returns = BeanFunctions.call(functions, "meat"); // log：狗在吃meat\n')])])])])}),[],!1,null,null,null);e.default=s.exports}}]);