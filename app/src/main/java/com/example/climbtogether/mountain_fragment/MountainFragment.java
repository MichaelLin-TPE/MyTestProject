package com.example.climbtogether.mountain_fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.climbtogether.R;
import com.example.climbtogether.login_activity.LoginActivity;
import com.example.climbtogether.mountain_fragment.db_modle.DataDTO;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static com.example.climbtogether.tool.Constant.ALL;
import static com.example.climbtogether.tool.Constant.LEVEL_A;
import static com.example.climbtogether.tool.Constant.LEVEL_B;
import static com.example.climbtogether.tool.Constant.LEVEL_C;
import static com.example.climbtogether.tool.Constant.LEVEL_C_PLUS;
import static com.example.climbtogether.tool.Constant.LEVEL_D;
import static com.example.climbtogether.tool.Constant.LEVEL_E;


public class MountainFragment extends Fragment implements MountainFragmentVu {

    private TextView tvAll, tvLevelA, tvLevelB, tvLevelC, tvLevelCpuls, tvLevelD, tvLevelE, tvWatchMore, tvSearchNoData;

    private RecyclerView recyclerView;

    private MountainFragmentPresenter presenter;

    private Context context;

    private MountainRecyclerViewAdapter adapter;

    private DatePicker datePicker;

    private FirebaseAuth mAuth;

    private FirebaseFirestore firestore;

    private String topTime;

    public static MountainFragment newInstance() {
        MountainFragment fragment = new MountainFragment();

        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        initPresenter();
    }

    private void initPresenter() {
        presenter = new MountainFragmentPresentImpl(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mountain, container, false);
        initView(view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        presenter.onPrepareData();
    }

    private void initView(View view) {
        tvAll = view.findViewById(R.id.mountain_fragment_all);
        tvLevelA = view.findViewById(R.id.mountain_fragment_a);
        tvLevelB = view.findViewById(R.id.mountain_fragment_b);
        tvLevelC = view.findViewById(R.id.mountain_fragment_c);
        tvLevelCpuls = view.findViewById(R.id.mountain_fragment_c_plus);
        tvLevelD = view.findViewById(R.id.mountain_fragment_d);
        tvLevelE = view.findViewById(R.id.mountain_fragment_e);
        tvWatchMore = view.findViewById(R.id.mountain_fragment_watch_more);
        recyclerView = view.findViewById(R.id.mountain_fragment_recycler_view);
        tvSearchNoData = view.findViewById(R.id.mountain_fragment_search_no_data);

        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        tvAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onFilterTextViewBackgroundChangeListener(ALL, tvAll.getText().toString());
            }
        });
        tvLevelA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onFilterTextViewBackgroundChangeListener(LEVEL_A, tvLevelA.getText().toString());
            }
        });
        tvLevelB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onFilterTextViewBackgroundChangeListener(LEVEL_B, tvLevelB.getText().toString());
            }
        });
        tvLevelC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onFilterTextViewBackgroundChangeListener(LEVEL_C, tvLevelC.getText().toString());
            }
        });
        tvLevelCpuls.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onFilterTextViewBackgroundChangeListener(LEVEL_C_PLUS, tvLevelCpuls.getText().toString());
            }
        });
        tvLevelD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onFilterTextViewBackgroundChangeListener(LEVEL_D, tvLevelD.getText().toString());
            }
        });
        tvLevelE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onFilterTextViewBackgroundChangeListener(LEVEL_E, tvLevelE.getText().toString());
            }
        });
        tvWatchMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onWatchMoreClickListener();
            }
        });
    }


    @Override
    public void showAllInformation() {
        AlertDialog dialog = new AlertDialog.Builder(context)
                .setMessage(context.getResources().getString(R.string.mountain_notice))
                .setNegativeButton(context.getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).create();
        dialog.show();
    }

    @Override
    public void showTextViewBackgroundChange(int textType) {
        tvAll.setBackground(textType == ALL ? ContextCompat.getDrawable(context, R.drawable.text_shapte) : null);
        tvLevelA.setBackground(textType == LEVEL_A ? ContextCompat.getDrawable(context, R.drawable.text_shapte) : null);
        tvLevelB.setBackground(textType == LEVEL_B ? ContextCompat.getDrawable(context, R.drawable.text_shapte) : null);
        tvLevelC.setBackground(textType == LEVEL_C ? ContextCompat.getDrawable(context, R.drawable.text_shapte) : null);
        tvLevelCpuls.setBackground(textType == LEVEL_C_PLUS ? ContextCompat.getDrawable(context, R.drawable.text_shapte) : null);
        tvLevelD.setBackground(textType == LEVEL_D ? ContextCompat.getDrawable(context, R.drawable.text_shapte) : null);
        tvLevelE.setBackground(textType == LEVEL_E ? ContextCompat.getDrawable(context, R.drawable.text_shapte) : null);
    }

    @Override
    public Context getVuContext() {
        return context;
    }

    @Override
    public void setRecyclerView(ArrayList<DataDTO> allInformation) {
        adapter = new MountainRecyclerViewAdapter(context);
        adapter.setData(allInformation);
        recyclerView.setAdapter(adapter);
        adapter.setOnMountainItemClickListener(new MountainRecyclerViewAdapter.OnMountainItemClickListener() {
            @Override
            public void onClick(DataDTO data) {

            }

            @Override
            public void onIconClick(int sid) {
                FirebaseUser user = mAuth.getCurrentUser();
                if (user != null) {
                    presenter.onShowDatePicker(sid);
                } else {
                    presenter.onLoginEvent();
                }


            }
        });

    }

    @Override
    public void showSearchNoDataInformation(boolean isShow) {
        tvSearchNoData.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }

    @Override
    public void setDataChange(ArrayList<DataDTO> dataDTO,String isSHow) {
        adapter.setData(dataDTO);
        adapter.notifyDataSetChanged();
        Toast.makeText(context, isSHow.equals("true") ? "此筆資料已加入我的戰績" : "已從我的戰績移除", Toast.LENGTH_LONG).show();
    }

    @Override
    public void showDatePick(final int sid) {
        datePicker = new DatePicker(context);

        AlertDialog dialog = new AlertDialog.Builder(context)
                .setView(datePicker)
                .setPositiveButton(getString(R.string.confirm), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Log.i("Michael", "時間為 : " + topTime);
                        presenter.onCreateDocumentInFirestore(sid, topTime);
                    }
                }).setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).create();
        dialog.show();

        datePicker.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker, int year, int month, int date) {
                String monthStr;
                String yearStr;
                String dateStr;
                if ((month + 1) < 10) {
                    monthStr = "0" + (month + 1);
                } else {
                    monthStr = (month + 1) + "";
                }
                if ((date) < 10) {
                    dateStr = "0" + date;
                } else {
                    dateStr = date + "";
                }
                yearStr = year + "";
                topTime = String.format(Locale.getDefault(), "%s/%s/%s", yearStr, monthStr, dateStr);
            }
        });
    }

    @Override
    public void setFirestore(final int sid, String topTime, DataDTO data) {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            String email = user.getEmail();
            String mountainName = data.getName();
            if (email != null) {
                Map<String, Object> map = new HashMap<>();
                map.put("name", data.getName());
                map.put("topTime", topTime);
                map.put("sid", data.getSid());
                firestore.collection("favorite").document(email).collection(mountainName).document(mountainName)
                        .set(map)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    String isShow = "true";
                                    presenter.onTopIconChange(sid,isShow);
                                }
                            }
                        });


            }
        }
    }

    @Override
    public void intentToLoginActivity() {
        Intent it = new Intent(context, LoginActivity.class);
        context.startActivity(it);
    }

    @Override
    public void deleteFavorite(final int sid, DataDTO dataDTO) {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            String email = user.getEmail();
            if (email != null) {
                firestore.collection("favorite").document(email).collection(dataDTO.getName()).document(dataDTO.getName())
                        .delete()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    String isShow = "false";
                                    presenter.onTopIconChange(sid,isShow);
                                }
                            }
                        });
            }
        }
    }


}
