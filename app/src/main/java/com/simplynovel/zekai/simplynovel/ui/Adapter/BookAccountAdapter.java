package com.simplynovel.zekai.simplynovel.ui.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.simplynovel.zekai.simplynovel.R;
import com.simplynovel.zekai.simplynovel.activity.AccountBookListActivity;
import com.simplynovel.zekai.simplynovel.activity.AccountBookQuestionActivity;
import com.simplynovel.zekai.simplynovel.activity.AccountExperienceGradeActivity;
import com.simplynovel.zekai.simplynovel.activity.AccountMyMessageActivity;
import com.simplynovel.zekai.simplynovel.activity.AccountPersonMessageActivity;
import com.simplynovel.zekai.simplynovel.activity.AccountPersonActivity;
import com.simplynovel.zekai.simplynovel.activity.AccountReadHistoryActivity;
import com.simplynovel.zekai.simplynovel.activity.AccountTopicActivity;
import com.simplynovel.zekai.simplynovel.activity.BookRackActivity;
import com.simplynovel.zekai.simplynovel.domain.DataAccount;
import com.simplynovel.zekai.simplynovel.ui.BookConversionDialog;
import com.simplynovel.zekai.simplynovel.utils.UIUtils;

/**
 * Created by 15082 on 2018/7/14.
 */

public class BookAccountAdapter extends RecyclerView.Adapter {
    private final static int ACCOUNT_NAME_TAG = 0x11;
    private final static int ACCOUNT_STATE_TAG = 0X12;
    private final static int ACCOUNT_IMG_TAG = 0X13;
    private final static int ACCOUNT_TEXT_TAG = 0X14;
    private DataAccount mDataAccount;
    private Context mContext;

    public BookAccountAdapter(DataAccount dataAccount, Context parentcontext) {
        mDataAccount = dataAccount;
        mContext = parentcontext;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return ACCOUNT_NAME_TAG;
        } else if (position == 1) {
            return ACCOUNT_STATE_TAG;
        } else if (position == 2 || position == 3) {
            return ACCOUNT_IMG_TAG;
        } else {
            return ACCOUNT_TEXT_TAG;
        }
    }


    class AccountNameViewHolder extends RecyclerView.ViewHolder {
        private TextView mAccountName;
        private ImageView mAccountIcon;
        private RelativeLayout mAccountMsg;

        public AccountNameViewHolder(View itemView) {
            super(itemView);
            mAccountMsg = (RelativeLayout) itemView.findViewById(R.id.account_msg);
            mAccountName = (TextView) itemView.findViewById(R.id.account_name_tv);
            mAccountIcon = (ImageView) itemView.findViewById(R.id.account_icon_img);
        }

        public void setmAccountName(String accountName) {
            this.mAccountName.setText(accountName);
        }

        public void setmAccountIcon(int imgres) {
            this.mAccountIcon.setImageResource(imgres);
        }
    }

    class AccountStateViewHolder extends RecyclerView.ViewHolder {
        private RelativeLayout mAccountState;
        private TextView mDesText;
        private ImageView mDesImg;
        private TextView mBookMoney;
        private TextView mBookToken;

        public AccountStateViewHolder(View itemView) {
            super(itemView);
            mAccountState = (RelativeLayout) itemView.findViewById(R.id.account_state);
            mDesText = (TextView) itemView.findViewById(R.id.account_des_text);
            mDesImg = (ImageView) itemView.findViewById(R.id.account_des_left_img);
            mBookMoney = (TextView) itemView.findViewById(R.id.book_money);
            mBookToken = (TextView) itemView.findViewById(R.id.book_token);
        }

        public void setmDesText(String des) {
            this.mDesText.setText(des);
        }

        public void setmDesImg(int imgres) {
            this.mDesImg.setImageResource(imgres);
        }

        public void setmBookMoney(String bookmoney) {
            this.mBookMoney.setText(bookmoney);
        }

        public void setmBookToken(String booktoken) {
            this.mBookToken.setText(booktoken);
        }
    }

    class AccountImgViewHolder extends RecyclerView.ViewHolder {
        private RelativeLayout mAccountInformation;
        private TextView mFunctionDes;
        private ImageView mFunctionIcon;
        private ImageView mDesImg;

        public AccountImgViewHolder(View itemView) {
            super(itemView);
            mAccountInformation = (RelativeLayout) itemView.findViewById(R.id.account_information);
            mFunctionDes = (TextView) itemView.findViewById(R.id.account_function_des);
            mFunctionIcon = (ImageView) itemView.findViewById(R.id.account_function_icon);
            mDesImg = (ImageView) itemView.findViewById(R.id.account_des_img);
        }

        public void setmFunctionDes(String des) {
            this.mFunctionDes.setText(des);
        }

        public void setmFunctionIcon(int imgres) {
            this.mFunctionIcon.setImageResource(imgres);
        }

        public void setmDesImg(int imgres) {
            this.mDesImg.setImageResource(imgres);
        }
    }

    class AccountTextViewHolder extends RecyclerView.ViewHolder {
        private RelativeLayout mAccountDesTextInformation;
        private TextView mFunctionDes;
        private ImageView mFunctionIcon;
        private TextView mDes;

        public AccountTextViewHolder(View itemView) {
            super(itemView);
            mAccountDesTextInformation = (RelativeLayout) itemView.findViewById(R.id.account_des_text_information);
            mFunctionDes = (TextView) itemView.findViewById(R.id.account_function_des);
            mFunctionIcon = (ImageView) itemView.findViewById(R.id.account_function_icon);
            mDes = (TextView) itemView.findViewById(R.id.account_des);
        }

        public void setmFunctionDes(String des) {
            this.mFunctionDes.setText(des);
        }

        public void setmFunctionIcon(int imgres) {
            this.mFunctionIcon.setImageResource(imgres);
        }

        public void setmDes(String des) {
            this.mDes.setText(des);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ACCOUNT_NAME_TAG) {
            AccountNameViewHolder holder = new AccountNameViewHolder(UIUtils.inflate(R.layout.bookaccount_rv_account_name));
            return holder;
        }
        if (viewType == ACCOUNT_STATE_TAG) {
            AccountStateViewHolder holder = new AccountStateViewHolder(UIUtils.inflate(R.layout.bookaccount_rv_account_state));
            return holder;
        }
        if (viewType == ACCOUNT_IMG_TAG) {
            AccountImgViewHolder holder = new AccountImgViewHolder(UIUtils.inflate(R.layout.bookaccount_rv_account_information));
            return holder;
        }
        if (viewType == ACCOUNT_TEXT_TAG) {
            AccountTextViewHolder holder = new AccountTextViewHolder(UIUtils.inflate(R.layout.bookaccount_rv_account_destext_information));
            return holder;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (position) {
                    case 0:
                        Intent intent = new Intent(mContext, AccountPersonMessageActivity.class);
                        mContext.startActivity(intent);
                        break;
                    case 1:
                        Intent intent_1 = new Intent(mContext, AccountPersonMessageActivity.class);
                        mContext.startActivity(intent_1);
                        break;
                    case 2:
                        Intent intent_2 = new Intent(mContext, AccountPersonActivity.class);
                        mContext.startActivity(intent_2);
                        break;
                    case 3:
                        Toast.makeText(UIUtils.getContext(), position + "", Toast.LENGTH_SHORT).show();
                        break;
                    case 4:
                        Intent intent_4 = new Intent(mContext, AccountExperienceGradeActivity.class);
                        mContext.startActivity(intent_4);
                        break;
                    case 5:
                        MyDialogShow();
                        break;
                    case 6:
                        MyMessage();
                        break;
                    case 7:
                        ReadHistory();
                        break;
                    case 8:
                        BookList();
                        break;
                    case 9:
                        Topic();
                        break;
                    case 10:
                        BookQuestion();
                        break;
                    case 11:
                        Toast.makeText(UIUtils.getContext(), position + "", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
            }
        });
        if (position == 0) {
            AccountNameViewHolder nameViewHolder = (AccountNameViewHolder) holder;
            nameViewHolder.setmAccountName(mDataAccount.getFunctionDes()[0]);
            nameViewHolder.setmAccountIcon(mDataAccount.getAccountImgRes());
        } else if (position == 1) {
            AccountStateViewHolder stateViewHolder = (AccountStateViewHolder) holder;
            stateViewHolder.setmBookMoney(mDataAccount.getFunctionDes()[1]);
            stateViewHolder.setmBookToken(mDataAccount.getFunctionDes()[2]);
            stateViewHolder.setmDesText(mDataAccount.getRightDes()[0]);
            stateViewHolder.setmDesImg(R.mipmap.baseline_account_box_black_24);
        } else if (position == 2) {
            AccountImgViewHolder imgViewHolder = (AccountImgViewHolder) holder;
            imgViewHolder.setmFunctionIcon(R.mipmap.baseline_account_box_black_24);
            imgViewHolder.setmFunctionDes("我的账户");
            imgViewHolder.setmDesImg(R.mipmap.baseline_account_box_black_24);
        } else if (position == 3) {
            AccountImgViewHolder imgViewHolder = (AccountImgViewHolder) holder;
            imgViewHolder.setmFunctionIcon(R.mipmap.baseline_account_box_black_24);
            imgViewHolder.setmFunctionDes("我的VIP");
            imgViewHolder.setmDesImg(R.mipmap.baseline_account_box_black_24);
        } else if (position == 4) {
            AccountTextViewHolder textViewHolder = (AccountTextViewHolder) holder;
            textViewHolder.setmFunctionIcon(R.mipmap.baseline_account_box_black_24);
            textViewHolder.setmFunctionDes("经验等级");
            textViewHolder.setmDes(mDataAccount.getRightDes()[1]);
        } else if (position == 5) {
            AccountTextViewHolder textViewHolder = (AccountTextViewHolder) holder;
            textViewHolder.setmFunctionIcon(R.mipmap.baseline_account_box_black_24);
            textViewHolder.setmFunctionDes("兑换中心");
            textViewHolder.setmDes("");
        } else if (position == 6) {
            AccountTextViewHolder textViewHolder = (AccountTextViewHolder) holder;
            textViewHolder.setmFunctionIcon(R.mipmap.baseline_account_box_black_24);
            textViewHolder.setmFunctionDes("我的消息");
            textViewHolder.setmDes("");
        } else if (position == 7) {
            AccountTextViewHolder textViewHolder = (AccountTextViewHolder) holder;
            textViewHolder.setmFunctionIcon(R.mipmap.baseline_account_box_black_24);
            textViewHolder.setmFunctionDes("阅读历史");
            textViewHolder.setmDes("");
        } else if (position == 8) {
            AccountTextViewHolder textViewHolder = (AccountTextViewHolder) holder;
            textViewHolder.setmFunctionIcon(R.mipmap.baseline_account_box_black_24);
            textViewHolder.setmFunctionDes("书单");
            textViewHolder.setmDes("");
        } else if (position == 9) {
            AccountTextViewHolder textViewHolder = (AccountTextViewHolder) holder;
            textViewHolder.setmFunctionIcon(R.mipmap.baseline_account_box_black_24);
            textViewHolder.setmFunctionDes("话题");
            textViewHolder.setmDes("");
        } else if (position == 10) {
            AccountTextViewHolder textViewHolder = (AccountTextViewHolder) holder;
            textViewHolder.setmFunctionIcon(R.mipmap.baseline_account_box_black_24);
            textViewHolder.setmFunctionDes("书荒提问");
            textViewHolder.setmDes("");
        } else if (position == 11) {
            AccountTextViewHolder textViewHolder = (AccountTextViewHolder) holder;
            textViewHolder.setmFunctionIcon(R.mipmap.baseline_account_box_black_24);
            textViewHolder.setmFunctionDes("设置");
            textViewHolder.setmDes("");
        }
    }

    private void BookQuestion() {
        Intent intent = new Intent(mContext, AccountBookQuestionActivity.class);
        mContext.startActivity(intent);
    }

    private void Topic() {
        Intent intent = new Intent(mContext, AccountTopicActivity.class);
        mContext.startActivity(intent);
    }

    private void BookList() {
        Intent intent = new Intent(mContext, AccountBookListActivity.class);
        mContext.startActivity(intent);
    }

    private void ReadHistory() {
        Intent intent = new Intent(mContext, AccountReadHistoryActivity.class);
        mContext.startActivity(intent);
    }

    private void MyMessage() {
        Intent intent = new Intent(mContext, AccountMyMessageActivity.class);
        mContext.startActivity(intent);
    }

    private void MyDialogShow() {
        final BookConversionDialog bookConversionDialog= new BookConversionDialog(mContext);
        Window dialogWindow = bookConversionDialog.getWindow();
        WindowManager.LayoutParams layoutParams = bookConversionDialog.getWindow().getAttributes();
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.width=WindowManager.LayoutParams.FILL_PARENT;
        layoutParams.alpha = 1;
        dialogWindow.setWindowAnimations(R.style.ConversionDialogAnim);
        dialogWindow.setAttributes(layoutParams);
        TextView confirm = bookConversionDialog.getConfirm();
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(UIUtils.getContext(),"confirm",Toast.LENGTH_SHORT).show();
            }
        });
        bookConversionDialog.show();
    }

    @Override
    public int getItemCount() {
        return 12;
    }
}
