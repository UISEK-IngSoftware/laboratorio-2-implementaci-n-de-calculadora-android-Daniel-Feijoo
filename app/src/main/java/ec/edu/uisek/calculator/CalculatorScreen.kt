package ec.edu.uisek.calculator

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import ec.edu.uisek.calculator.ui.theme.Purple40
import ec.edu.uisek.calculator.ui.theme.Purple80
import ec.edu.uisek.calculator.ui.theme.UiSekBlue

@Composable
fun CalculatorScreen(
    viewModel: CalculatorViewModel = viewModel()
) {
    val state = viewModel.state

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(bottom = 8.dp),
            contentAlignment = Alignment.BottomEnd
        ) {
            Text(
                text = state.display,
                fontSize = 56.sp,
                textAlign = TextAlign.End,
                color = Color.White,
                modifier = Modifier.fillMaxWidth()
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(2f),
            contentAlignment = Alignment.Center
        ) {
            CalculatorGrid(onEvent = viewModel::onEvent)
        }
    }
}
@Composable
fun CalculatorGrid(onEvent: (CalculatorEvent) -> Unit) {
    val buttons = listOf(
        "7", "8", "9", "÷",
        "4", "5", "6", "×",
        "1", "2", "3", "−",
        "0", ".", "=", "+"
    )

    LazyVerticalGrid(columns = GridCells.Fixed(4)) {
        items(buttons.size) { index ->
            val label = buttons[index]
            CalculatorButton(label = label) {
                when (label) {
                    in "0".."9" -> onEvent(CalculatorEvent.Number(label))
                    "." -> onEvent(CalculatorEvent.Decimal)
                    "=" -> onEvent(CalculatorEvent.Calculate)
                    else -> onEvent(CalculatorEvent.Operator(label))
                }
            }
        }

        item(span = { GridItemSpan(1) }) { CalculatorButton(label = "AC") { onEvent(CalculatorEvent.AllClear) } }
        item {}
        item { CalculatorButton(label = "C") { onEvent(CalculatorEvent.Clear) } }
    }
}



@Composable
fun CalculatorButton(label: String, onClick: () -> Unit) {
    // Definimos los colores
    val backgroundColor = when {
        label in "0".."9" -> Color(0xFFFF8C00) // Naranja menos brillante para los números
        label in listOf("÷", "×", "−", "+", "=", ".", "AC", "C") -> Color(0xFFFF6347) // Rojo claro para operaciones, AC, C y "."
        else -> UiSekBlue // Azul para otros botones (si los hubiera)
    }

    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .fillMaxSize()
            .clip(CircleShape)
            .background(backgroundColor) // Aplicamos el color de fondo según el botón
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = label,
            color = Color.White, // El texto siempre será blanco
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            maxLines = 1
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CalculatorPreview() {
    CalculatorScreen()
}