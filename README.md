# HeaderHost
修改Minecraft头部连接IP

由于Minecraft连接到服务器就像HTTP连接一样，会发送自己连接使用的Host，导致部分服务器（点名批评Hypixel）会进行验证，必须用他家的IP才能连接进入，以至于无法使用加速IP进入。  
除了在加速IP上动手脚，也可以在客户端上动手脚，不发送真实连接IP

# 配置
一般位于`.mineecraft/config/headerhost.xml`
```xml
<?xml version="1.0" encoding="UTF-8" ?>
<configuration>
    <headerhosts>
        <!-- 将[my.host.to.hypixel:25565]的头部写为[mc.hypixel.net:25565] -->
        <headerhost>
            <from port="25565">my.host.to.hypixel</from>
            <to port="25565">mc.hypixel.net</to>
        </headerhost>
        <!-- 将[more.host.to.hypixel:25565]的头部写为[mc.hypixel.net:25565] -->
        <headerhost>
            <from port="25565">more.host.to.hypixel</from>
            <to port="25565">mc.hypixel.net</to>
        </headerhost>
    </headerhosts>
</configuration>
```
应该都顾名思义了吧，想要多添加几个规则就多添加几个`headerhost`元素，基本上就长这样
```xml
<headerhost>
    <from port="原端口">原IP</from>
    <to port="伪装端口">伪装IP</to>
</headerhost>
```

# 兼容性
其他开发者们你们好！
本mod的工作原理是注入了`net.minecraft.network.NetworkManager`类中的`sendPacket(net.minecraft.network.Packet)`方法，
会在其头部进行判断是否为`C00Handshake`；如果是，则会与规则一一匹配，匹配到就会替换掉