package devpkjain.instasnaps.utils;

import android.graphics.Color;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.CharacterStyle;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import java.util.StringTokenizer;

public class InstagramFormatter {

    private static final int INSTAGRAM_COLOR = Color.parseColor("#125688");
    private static final SpannableString _SPACE = new SpannableString(" ");

    public static CharSequence formatLikes(String input) {
        return colorizedAndBold(new SpannableString(input));
    }

    public static CharSequence formatComment(String input) {
        CharSequence result = new SpannableString("");

        StringTokenizer tokenizer = new StringTokenizer(input);
        TokenType tokenType;
        SpannableString token;
        int index = 0;

        while (tokenizer.hasMoreTokens()) {
            result = TextUtils.concat(result, _SPACE);
            token = new SpannableString(tokenizer.nextElement().toString());
            tokenType = _determineTokenType(token, index);
            switch (tokenType) {
                case FIRST:
                    result = TextUtils.concat(result, colorizedAndBold(token));
                    break;
                case USERNAME:
                case HASHTAG:
                    result = TextUtils.concat(result, colorized(token));
                    break;
                case DEFAULT:
                default:
                    result = TextUtils.concat(result, token);
                    break;
            }

            index++;
        }

        return result;
    }

    private static TokenType _determineTokenType(SpannableString input, int index) {
        if (index == 0) {
            return TokenType.FIRST;
        } else {
            char c = input.charAt(0);
            if (c == '#') {
                return TokenType.HASHTAG;
            } else if (c == '@') {
                return TokenType.USERNAME;
            } else {
                return TokenType.DEFAULT;
            }
        }
    }

    private static Spannable colorized(Spannable input) {
        return applySpan(input, new ForegroundColorSpan(INSTAGRAM_COLOR));
    }

    private static Spannable bold(Spannable input) {
        return applySpan(input, new StyleSpan(Typeface.BOLD));
    }

    private static Spannable colorizedAndBold(Spannable input) {
        return colorized(bold(input));
    }

    private static Spannable applySpan(Spannable input, CharacterStyle span) {
        input.setSpan(span, 0, input.length(), 0);
        return input;
    }

    private enum TokenType {
        FIRST, USERNAME, HASHTAG, DEFAULT
    }
}
