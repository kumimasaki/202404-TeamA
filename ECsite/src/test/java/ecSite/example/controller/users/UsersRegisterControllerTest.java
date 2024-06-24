package ecSite.example.controller.users;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import ecSite.example.model.entity.UsersEntity;
import ecSite.example.service.UsersService;

@SpringBootTest
@AutoConfigureMockMvc
public class UsersRegisterControllerTest {
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private UsersService usersService;
	
	// usersEntityのnotconfirmedUserの情報を作成
    UsersEntity notConfirmedUser = new UsersEntity("test", "test@test.com", "1234");

	// サービスクラスを使ったデータ作成
	@BeforeEach
	public void prepareDate() {
		// 登録できる場合"test@test.com"."1234" true
		when(usersService.createNotConfirmedUser("test", "test@test.com", "1234")).thenReturn(true);
		// 登録できない場合　"123@test.com", パスワードはどんな値でも良い　　false
		when(usersService.createNotConfirmedUser("Taro", "test@test.com", "1234")).thenReturn(false);
        // notconfirmedUserの情報を用意する
        when(usersService.getNotConfirmedUser()).thenReturn(notConfirmedUser);
	}
	
	// ユーザー登録画面が正常に表示できるテスト
	@Test
	public void testgetUsersRegisterPage() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders
				.get("/user/register");
		mockMvc.perform(request)
				.andExpect(view().name("user/user_register.html"))
				.andExpect(model().attributeExists("notConfirmedUser"));
	}
	
	// ユーザーの確認が成功するかのテスト
	@Test
	public void testUsersConfirm_Succeed() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders
				.post("/user/confirm")
				.param("userName", "test")
				.param("userEmail", "test@test.com")
				.param("userPassword", "1234");

		mockMvc.perform(request)
				//"user_register_confirm.html" が表示されることを検証
				.andExpect(view().name("user/user_register_confirm.html"));
		
        // usersService.createNotConfirmedUserメソッドが指定された引数で1回呼び出されたことを検証
        verify(usersService).createNotConfirmedUser("test", "test@test.com", "1234");
        // usersService.getNotConfirmedUseメソッドが呼び出されたことを検証
        verify(usersService).getNotConfirmedUser();
	}
	
    // ユーザーの確認が失敗するかのテスト
    @Test
    public void testUsersConfirm_Fail() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders
        		.post("/user/confirm")
                .param("userName", "Taro")
                .param("userEmail", "test@test.com")
                .param("userPassword", "1234");

        mockMvc.perform(request)
        		// user_register.html" が表示されることを検証
                .andExpect(view().name("user/user_register.html"))
                // エラーメッセージが表示されることを検証
                .andExpect(model().attributeExists("error"));

        // メソッド呼び出しの検証
        verify(usersService).createNotConfirmedUser("Taro", "test@test.com", "1234");
    }

    // ユーザー登録処理のテスト
    @Test
    public void testUsersRegisterProcess() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders
        		.post("/user/register/process");
        mockMvc.perform(request)
                .andExpect(view().name("redirect:/user/login"));
        
        // createConfirmedUserメソッド呼び出しの検証
        verify(usersService).createConfirmedUser();
    }
}
