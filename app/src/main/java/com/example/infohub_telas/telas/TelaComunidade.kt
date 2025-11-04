package com.example.infohub_telas.telas

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChatBubble
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.infohub_telas.R
import com.example.infohub_telas.components.BottomAppBarWithAnimation
import com.example.infohub_telas.navigation.Routes
import com.example.infohub_telas.ui.theme.InfoHub_telasTheme
import com.example.infohub_telas.ui.theme.PrimaryOrange

// Data class para representar um post da comunidade
data class CommunityPost(
    val id: Int,
    val title: String,
    val description: String,
    val author: String,
    val rating: Float,
    val isHighlighted: Boolean = false
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaComunidade(navController: NavController) {
    var currentRoute by remember { mutableStateOf(Routes.COMUNIDADE) }
    
    // Posts de exemplo
    val posts = remember {
        listOf(
            CommunityPost(
                id = 1,
                title = "Ótimo atendimento e produtos de qualidade",
                description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.",
                author = "João Silva",
                rating = 5f,
                isHighlighted = false
            ),
            CommunityPost(
                id = 2,
                title = "Ótimo atendimento e produtos de qualidade",
                description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.",
                author = "Maria Santos",
                rating = 4.5f,
                isHighlighted = true
            ),
            CommunityPost(
                id = 3,
                title = "Ótimo atendimento e produtos de qualidade",
                description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.",
                author = "Pedro Costa",
                rating = 5f,
                isHighlighted = false
            ),
            CommunityPost(
                id = 4,
                title = "Ótimo atendimento e produtos de qualidade",
                description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.",
                author = "Ana Oliveira",
                rating = 4f,
                isHighlighted = false
            )
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { },
                navigationIcon = {
                    Box(
                        modifier = Modifier
                            .padding(start = 12.dp)
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(Color.White),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.infohublogo),
                            contentDescription = "Logo",
                            modifier = Modifier.size(28.dp)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = PrimaryOrange
                )
            )
        },
        bottomBar = {
            BottomAppBarWithAnimation(
                navController = navController,
                currentRoute = currentRoute,
                onTabSelected = { route -> currentRoute = route }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /* Ação para criar novo post */ },
                containerColor = PrimaryOrange,
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .size(56.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.ChatBubble,
                    contentDescription = "Criar post",
                    tint = Color.White
                )
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF5F5F5))
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Header
            item {
                Spacer(modifier = Modifier.height(8.dp))
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                ) {
                    Text(
                        text = "Postagens da Comunidade",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = PrimaryOrange
                    )
                    Text(
                        text = "Organize suas compras e encontre as melhores promoções",
                        fontSize = 14.sp,
                        color = Color(0xFF666666),
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }

            // Posts
            items(posts) { post ->
                CommunityPostCard(post = post)
            }

            // Espaço extra no final
            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
fun CommunityPostCard(post: CommunityPost) {
    var isLiked by remember { mutableStateOf(false) }
    
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Header com avatar e nome
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Avatar
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(PrimaryOrange),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = post.author.first().toString(),
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                }
                
                Spacer(modifier = Modifier.width(12.dp))
                
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = post.author,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = Color(0xFF333333)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Título
            Text(
                text = post.title,
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
                color = Color(0xFF333333)
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Descrição
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(
                        if (post.isHighlighted) 
                            PrimaryOrange 
                        else 
                            Color(0xFFFFF3E0)
                    )
                    .padding(12.dp)
            ) {
                Text(
                    text = post.description,
                    fontSize = 14.sp,
                    color = if (post.isHighlighted) Color.White else Color(0xFF333333),
                    maxLines = 4,
                    overflow = TextOverflow.Ellipsis,
                    lineHeight = 20.sp
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Footer com ações e rating
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Ações (like, comentar, compartilhar)
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = { isLiked = !isLiked },
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            imageVector = if (isLiked) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription = "Like",
                            tint = if (isLiked) Color.Red else Color(0xFF666666),
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    
                    IconButton(
                        onClick = { /* Comentar */ },
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.ChatBubble,
                            contentDescription = "Comentar",
                            tint = Color(0xFF666666),
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }

                // Rating
                Row(
                    horizontalArrangement = Arrangement.spacedBy(2.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    repeat(5) { index ->
                        Icon(
                            imageVector = if (index < post.rating.toInt()) 
                                Icons.Default.Star 
                            else 
                                Icons.Default.StarBorder,
                            contentDescription = "Star ${index + 1}",
                            tint = PrimaryOrange,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TelaComunidadePreview() {
    InfoHub_telasTheme {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            TelaComunidade(navController = rememberNavController())
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CommunityPostCardPreview() {
    InfoHub_telasTheme {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            CommunityPostCard(
                post = CommunityPost(
                    id = 1,
                    title = "Ótimo atendimento e produtos de qualidade",
                    description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
                    author = "João Silva",
                    rating = 5f,
                    isHighlighted = true
                )
            )
        }
    }
}
