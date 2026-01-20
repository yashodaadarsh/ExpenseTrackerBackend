import re

class MessageUtil():
    """
    Utility class for analyzing SMS messages, particularly to determine 
    if a message is related to a financial transaction (Bank SMS).
    """

    def isBankSms(self, message: str) -> bool:
        """
        Checks if a message contains keywords commonly found in bank or 
        transactional SMS, ignoring case.

        Args:
            message: The string content of the SMS.

        Returns:
            True if financial keywords are found, False otherwise.
        """
        # Enhanced list of keywords for robust bank SMS detection
        words_to_search = [
            # Core debit indicators
            'debited', 'debit', 'spent', 'spend', 'paid', 'paying',

            # Payment methods (usually debit when combined with verbs)
            'upi', 'card', 'atm', 'pos', 'netbanking', 'online',

            # Transaction context
            'purchase', 'txn', 'transaction', 'merchant', 'store',
            'transfer to', 'sent to',

            # Amount indicators (used mostly in debit SMS)
            'rs', 'inr', 'amount', 'amt',

            # Wallet / app spends
            'wallet', 'google pay', 'phonepe', 'paytm'
        ]
        
        # Create a case-insensitive, whole-word-match regular expression pattern
        # r'\b(?:word1|word2|word3)\b' ensures we match 'card' but not 'discard'

        pattern = r'\b(?:' + '|'.join(re.escape(word) for word in words_to_search) + r')\b'
        
        # Search the message for the pattern
        return bool(re.search(pattern, message, re.IGNORECASE))