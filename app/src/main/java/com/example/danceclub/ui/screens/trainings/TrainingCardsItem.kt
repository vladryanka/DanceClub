package com.example.danceclub.ui.screens.trainings

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.outlined.Circle
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.danceclub.R
import com.example.danceclub.data.model.Training
import kotlinx.coroutines.launch
import java.time.LocalDate

// TODO: Добавить фильтр по месяцу

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrainingCardsItem(
    contentPadding: PaddingValues,
    updateCurrentTrainings: (Training) -> Unit,
    changeVisibility: () -> Unit,
    currentMonthTrainings: (Int) -> List<Training>?,
) {
    val months = listOf(
        "Все", "Январь", "Февраль", "Март", "Апрель", "Май", "Июнь",
        "Июль", "Август", "Сентябрь", "Октябрь", "Ноябрь", "Декабрь"
    )
    var expanded by remember { mutableStateOf(false) }

    var selectedMonth by remember { mutableStateOf(months[LocalDate.now().monthValue]) }
    val isChecked: Boolean by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    var trainingList = currentMonthTrainings(LocalDate.now().monthValue)

    Column(
        modifier = Modifier
            .padding(top = contentPadding.calculateTopPadding()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = it }) {
            TextField(
                modifier = Modifier.menuAnchor(MenuAnchorType.PrimaryNotEditable),
                value = selectedMonth,
                onValueChange = {},
                readOnly = true,
                singleLine = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.clip(RoundedCornerShape(60.dp))
            ) {
                months.forEach { month ->
                    DropdownMenuItem(
                        text = { Text(text = month) },
                        onClick = {
                            selectedMonth = month
                            trainingList = currentMonthTrainings(months.indexOf(month))
                            expanded = false
                        },
                    )
                }
            }
        }

        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            contentPadding = PaddingValues(16.dp)
        ) {
            itemsIndexed(trainingList ?: emptyList()) { _, item ->
                Column(
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .background(Color.LightGray)
                        .clip(RoundedCornerShape(16.dp))
                        .clickable(onClick = {
                            updateCurrentTrainings(item)
                            Log.d("Doing", item.toString())
                            if (item.freeSpace > 0)
                                changeVisibility()
                            else {
                                scope.launch {
                                    snackbarHostState.showSnackbar(
                                        "Свободных мест нет",
                                        withDismissAction = true,
                                        duration = SnackbarDuration.Short
                                    )
                                }
                            }
                        })

                ) {
                    Card {
                        Row {
                            Icon(
                                imageVector = if (isChecked)
                                    Icons.Default.CheckCircle
                                else Icons.Outlined.Circle,
                                contentDescription = stringResource(R.string.icon_check)
                            )
                            Text(item.date.toString())
                        }
                    }
                    Text(text = item.name, style = TextStyle(fontSize = 24.sp))

                    Row {
                        Column {
                            Text(
                                text = item.trainerName,
                                color = Color.Black,
                                maxLines = 1,
                                modifier = Modifier.fillMaxWidth(),
                                style = TextStyle(fontSize = 24.sp)
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "1 ч",
                                color = Color.Gray,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                style = TextStyle(fontSize = 16.sp)
                            )
                        }
                    }
                    Text(
                        text = "${item.price} ₽",
                        color = Color.Gray,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = TextStyle(fontSize = 16.sp)
                    )
                }
            }
        }

    }
}