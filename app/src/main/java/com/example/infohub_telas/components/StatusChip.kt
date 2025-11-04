package com.example.infohub_telas.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.infohub_telas.model.CompanyStatus
import com.example.infohub_telas.ui.theme.StatusActive
import com.example.infohub_telas.ui.theme.StatusInactive
import com.example.infohub_telas.ui.theme.StatusPending

@Composable
fun StatusChip(
    status: CompanyStatus,
    modifier: Modifier = Modifier
) {
    val (backgroundColor, textColor) = when (status) {
        CompanyStatus.ACTIVE -> StatusActive to Color.White
        CompanyStatus.INACTIVE -> StatusInactive to Color.White
        CompanyStatus.PENDING -> StatusPending to Color.White
    }

    Surface(
        shape = RoundedCornerShape(16.dp),
        color = backgroundColor,
        modifier = modifier.padding(4.dp)
    ) {
        Text(
            text = status.name,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            color = textColor
        )
    }
}
