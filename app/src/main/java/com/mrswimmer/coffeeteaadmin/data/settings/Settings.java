package com.mrswimmer.coffeeteaadmin.data.settings;

public interface Settings {
    String coffeKinds[] = {"Арабика", "Робуста", "Либерика", "Эксцельза"};
    String teaKinds[] = {"Черный", "Зеленый", "Белый", "Желтый", "Улун", "Пуэр"};
    boolean checked[] = {true, true, true, true, true, true};
    String SORTED_SET = "sorted set";
    String SORT = "sort";
    String USER_ID = "user id";
    String USERNAME = "username";
}
