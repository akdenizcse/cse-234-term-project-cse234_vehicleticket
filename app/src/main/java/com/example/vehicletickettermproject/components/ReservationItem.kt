package com.example.vehicletickettermproject.components


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material.Card
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.vehicletickettermproject.R
import com.example.vehicletickettermproject.data.BusJourney
import com.example.vehicletickettermproject.data.Reservation


@Composable
fun ReservationItem(reservation: Reservation, journey: BusJourney, onCancelReservation: (Reservation) -> Unit,isCancellable:Boolean = false) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = 4.dp,
        backgroundColor = Color(0xFFF5F5F5)
    ) {
        Column(
            modifier = Modifier
                .background(
                    colorResource(id = R.color.alternative),
                    shape = RoundedCornerShape(10.dp)
                )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(7.dp)
                ) {
                    Text(
                        text = "${journey.fromPlace}",
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight(800),
                            color = Color(0xFF764754),
                            textAlign = TextAlign.Center,
                        ),
                        modifier = Modifier
                            .padding(top = 14.dp)
                    )
                    Text(
                        text = "->",
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight(800),
                            color = Color(0xFFD24545),
                            textAlign = TextAlign.Center,
                        ),
                        modifier = Modifier
                            .padding(top = 13.dp)
                    )
                    Text(
                        text = "${journey.toPlace}",
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight(800),
                            color = Color(0xFF764754),
                            textAlign = TextAlign.Center,
                        ),
                        modifier = Modifier
                            .padding(top = 14.dp)
                    )
                }
                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = "${journey.price} TL",
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight(800),
                            color = Color(0xFF764754),
                            textAlign = TextAlign.Center,
                        ),
                        modifier = Modifier.padding(top = 14.dp)
                    )
                }
            }

            Row(
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(top = 15.dp, start = 16.dp)
                ) {
                    Text(
                        text = "Date:",
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontWeight = FontWeight(800),
                            color = Color(0xFFD24545),
                            textAlign = TextAlign.Center,
                        )
                    )
                    Text(
                        text = "${journey.getFormattedBeginDate()}",
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontWeight = FontWeight(800),
                            color = Color(0xFF764754),
                            textAlign = TextAlign.Center,
                        )
                    )
                }
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(top = 15.dp, start = 19.dp)
                ) {
                    Text(
                        text = "Time:",
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontWeight = FontWeight(800),
                            color = Color(0xFFD24545),
                            textAlign = TextAlign.Center,
                        )
                    )
                    Text(
                        text = "${journey.getFormattedBeginTime()}",
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontWeight = FontWeight(800),
                            color = Color(0xFF764754),
                            textAlign = TextAlign.Center,
                        )
                    )
                }

            }
            Row(
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(top = 15.dp, start = 15.dp)
                ) {
                    Text(
                        text = "Duration:",
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontWeight = FontWeight(800),
                            color = Color(0xFFD24545),
                            textAlign = TextAlign.Center,
                        )
                    )
                    Text(
                        text = "${journey.duration}",
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontWeight = FontWeight(800),
                            color = Color(0xFF764754),
                            textAlign = TextAlign.Center,
                        )
                    )
                }
                if(isCancellable) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(top = 15.dp, start = 25.dp)
                    ) {
                        Text(
                            text = "Seat:",
                            style = TextStyle(
                                fontSize = 14.sp,
                                fontWeight = FontWeight(800),
                                color = Color(0xFFD24545),
                                textAlign = TextAlign.Center,
                            )
                        )
                        Text(
                            text = "${reservation.seatNumber}",
                            style = TextStyle(
                                fontSize = 14.sp,
                                fontWeight = FontWeight(800),
                                color = Color(0xFF764754),
                                textAlign = TextAlign.Center,
                            )
                        )
                    }
                } else {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(top = 15.dp, start = 32.dp)
                    ) {
                        Text(
                            text = "Seat:",
                            style = TextStyle(
                                fontSize = 14.sp,
                                fontWeight = FontWeight(800),
                                color = Color(0xFFD24545),
                                textAlign = TextAlign.Center,
                            )
                        )
                        Text(
                            text = "${reservation.seatNumber}",
                            style = TextStyle(
                                fontSize = 14.sp,
                                fontWeight = FontWeight(800),
                                color = Color(0xFF764754),
                                textAlign = TextAlign.Center,
                            )
                        )
                    }
                }
            }

            if(isCancellable) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically,

                    ) {
                    Button(
                        onClick = { onCancelReservation(reservation) },
                        modifier = Modifier
                            .padding(14.dp)
                            .width(140.dp)
                            .height(27.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD24545)),
                        shape = RoundedCornerShape(5.dp),
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Text(
                            text = "Cancel Reservation",
                            style = TextStyle(
                                fontSize = 14.sp,
                                fontWeight = FontWeight(800),
                                color = Color(0xFFE4DEBE),
                                textAlign = TextAlign.Center,
                            ),
                            modifier = Modifier.fillMaxWidth(),
                        )
                    }
                }
            } else {
                Spacer(modifier = Modifier.height(15.dp))
            }
        }
    }
}