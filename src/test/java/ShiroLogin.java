import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;

/**
 * @author:ljn
 * @Description:
 * @Date:2020/12/01 20:01
 */
public class ShiroLogin {

    //后台认证方法
    public static void textlogin(String username,String passwrd){
        //初始化安全管理器工厂
        IniSecurityManagerFactory factory = new IniSecurityManagerFactory("classpath:shiro.ini");
        //根据安全管理器工厂初始化安全管理器
        SecurityManager instance = factory.createInstance();
        //将安全管理器交给安全工具类
        SecurityUtils.setSecurityManager(instance);
        //根据安全工具类获取主体对象
        Subject subject = SecurityUtils.getSubject();
        //创建令牌 token=身份信息(username)+凭证信息(password)
        UsernamePasswordToken token = new UsernamePasswordToken(username, passwrd);
        //认证
        try {
            subject.login(token);
        } catch (UnknownAccountException  e) {
            System.out.println("未知的账号异常   用户名不正确");
        }catch (IncorrectCredentialsException e){
            System.out.println("不正确的凭证异常   密码错误");
        }
        /**
         *@return:UnknownAccountException:未知的账号异常,用户名不正确
         *@return:IncorrectCredentialsException:不正确的凭证异常,密码错误
        */
        boolean authenticated = subject.isAuthenticated();
        System.out.println("认证状态为"+authenticated);
    }

    public static void main(String[] args) {
        textlogin("ljn","123");
    }
}
