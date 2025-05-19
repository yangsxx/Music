package top.yangsc.ai;

import com.volcengine.ark.runtime.model.completion.chat.ChatCompletionRequest;
import com.volcengine.ark.runtime.model.completion.chat.ChatMessage;
import com.volcengine.ark.runtime.model.completion.chat.ChatMessageRole;
import com.volcengine.ark.runtime.service.ArkService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述：top.yangsc.swiftcache.ai
 *
 * @author yang
 * @date 2025/5/16 10:45
 */
@Service
public class HuoShanAiService implements BaseAiService {

    @Value("${ark-ai.api-key}")
    private String apiKey;

    @Value("${ark-ai.model-id}")
    private String modelId;

    @Override
    public String simpleGenerateText(String question) {
        if (question == null || question.isEmpty()) {
            return "";
        }
        // 创建ArkService实例
        ArkService arkService = ArkService.builder().apiKey(apiKey)
                .build();

        // 初始化消息列表
        List<ChatMessage> chatMessages = new ArrayList<>();

        // 创建用户消息
        ChatMessage userMessage = ChatMessage.builder()
                .role(ChatMessageRole.USER) // 设置消息角色为用户
                .content(question) // 设置消息内容
                .build();
        ChatMessage userMessage2 = ChatMessage.builder()
                .role(ChatMessageRole.SYSTEM) // 设置消息角色为用户
                .content("这是一个用于个人数据库的应用程序，你作为专业的数据管理专家，请根据要求协助处理数据！") // 设置消息内容
                .build();

        // 将用户消息添加到消息列表
        chatMessages.add(userMessage);

        // 创建聊天完成请求
        ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest.builder()
                .model(modelId)// 需要替换为Model ID
                .maxTokens(512)
                .messages(chatMessages) // 设置消息列表
                .build();

        StringBuilder response = new StringBuilder();
        // 发送聊天完成请求并打印响应
        try {
            // 获取响应并打印每个选择的消息内容
            arkService.createChatCompletion(chatCompletionRequest)
                    .getChoices()
                    .forEach(choice -> response.append(choice.getMessage().getContent()));
        } catch (Exception e) {
            System.out.println("请求失败: " + e.getMessage());
        } finally {
            // 关闭服务执行器
            arkService.shutdownExecutor();
        }
        return response.toString();
    }
}
