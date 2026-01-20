from app.utils.MessageUtil import MessageUtil
from app.service.llmService import LLMService
class MessageService:
    def __init__(self):
        self.messageUtil = MessageUtil()
        self.llmService = LLMService()

    def process_message(self,message):
        if self.messageUtil.isBankSms(message):
            print("Bank SMS detected. Processing with LLMService.  Message:", message)
            return self.llmService.runLLM(message)
        else:
            print("Not a Bank SMS. Ignoring message:", message)
            return None