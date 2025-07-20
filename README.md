# mcdrnamefix
修复由customname mod导致的控制台玩家名称为修改名称于是MCDR无法正确运行的bug</br>
mod构建已尽可能减小体积！</br>
</br>
mod仅对服务端broadcast的logChatMessage方法调用的params参数进行mixin，</br>
修改params为玩家真实名称以达到目的，这种情况下，</br>
玩家本地回显仍然不变（为customname mod修改的名称），</br>
只有服务端后台显示改变，以方便MCDR进行兼容处理。</br>
</br>
## 后台输出名称变为customname mod修改名称的原因解析
罪魁祸首为这一行调用中的MessageType.params(MessageType.CHAT, this.player)：
<img width="1069" height="158" alt="image" src="https://github.com/user-attachments/assets/487567e1-e7fb-4c6b-b5d4-3ca900bbe52a" />

因为它实际上内部的处理为entity.getDisplayName()而非entity.getName()：
<img width="1045" height="216" alt="image" src="https://github.com/user-attachments/assets/304c58aa-66cc-4b4d-8b93-68e7f13b5123" />

又因为customname mod对PlayerEntity的getDisplayName()进行注入以替换为自定义显示名称：
<img width="1110" height="452" alt="image" src="https://github.com/user-attachments/assets/f72a093a-c24e-4ed5-8ae4-84c609b3b2e5" />

所以最后这里的服务端logger输出被改变，导致MCDR无法得到玩家正确名称：
<img width="1303" height="446" alt="image" src="https://github.com/user-attachments/assets/ac88c660-e0b5-493e-9890-fe3f13916ad4" />

## 我的做法
1：修改函数调用参数 2：指定需要注入的方法 3：被修改参数的方法 4：获取待修改的方法的所有参数列表 5：获取目标玩家实体（后面用于获取玩家原始名称） 6：确保玩家实体不为null的情况下 7：重新构造一个用玩家原始名称的参数 8：用构造的参数覆盖4参数列表中的参数
<img width="1551" height="398" alt="image" src="https://github.com/user-attachments/assets/fceece6f-f19e-4d1b-8f91-aef4341900af" />

1：这就是前面的5，即玩家实体，用于获取玩家名称 2：这个地方就是我注入并被替换的参数（如果玩家不是null则替换） 3：此处未更改，所以玩家看到的是customname mod修改过的名称，而服务端后台输出的则是玩家原始名称
<img width="1186" height="328" alt="image" src="https://github.com/user-attachments/assets/ed36624b-85c2-4d30-8558-f55fc18086a0" />
