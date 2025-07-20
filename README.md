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
实际上很简单：
<img width="1551" height="398" alt="image" src="https://github.com/user-attachments/assets/fceece6f-f19e-4d1b-8f91-aef4341900af" />
<img width="1186" height="328" alt="image" src="https://github.com/user-attachments/assets/ed36624b-85c2-4d30-8558-f55fc18086a0" />
