package nl.hsleiden.investore.ui.statistics;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

import nl.hsleiden.investore.data.database.InvestoreDB;
import nl.hsleiden.investore.data.model.Item;
import nl.hsleiden.investore.data.tools.ItemsStatistics;
import nl.hsleiden.investore.data.tools.ItemDataToStringTool;
import nl.hsleiden.investore.databinding.FragmentStatisticsBinding;

public class StatisticsFragment extends Fragment {

    private FragmentStatisticsBinding binding;

    private InvestoreDB investoreDB;
    private ItemsStatistics stats;
    private ItemDataToStringTool stringTool;

    private ArrayList<Item> items;


    private String currency;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentStatisticsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        initialiseVariables();
        setUpStatisticFields();

        return root;
    }

    private void initialiseVariables() {
        loadDatabase();
        items = investoreDB.getAllItems();
        stats = new ItemsStatistics(items);
        stringTool = new ItemDataToStringTool();
        initialiseCurrency();
    }

    private void initialiseCurrency() {
        currency = "€"; // ToDo not hardcode currency
    }

    private void loadDatabase() {
        if (investoreDB == null) {
            investoreDB = new InvestoreDB(binding.getRoot().getContext());
        }
    }

    private void setUpStatisticFields() {
        String totalProfit = stringTool.doubleInCurrency(stats.getTotalProfit(), currency);
        binding.totalProfit.setText(totalProfit);
        String averagePercentage = stringTool.doubleInPercentage(stats.getAverageProfitPercentage());
        binding.averagePercentage.setText(averagePercentage);

        String totalSoldProfit = stringTool.doubleInCurrency(stats.getTotalSoldProfit(), currency);
        binding.totalSoldProfit.setText(totalSoldProfit);
        String averageSoldPercentage = stringTool.doubleInPercentage(stats.getAverageSoldProfitPercentage());
        binding.averageSoldPercentage.setText(averageSoldPercentage);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}