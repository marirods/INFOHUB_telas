# INFOHUB - Aplicativo de Localização e Estabelecimentos

## 📱 Sobre o Projeto
Aplicativo Android desenvolvido em Kotlin com Jetpack Compose para localização de endereços e busca de estabelecimentos próximos.

## 🗺️ Funcionalidades de Localização

### Busca de Endereços
- Busca por CEP usando ViaCEP API
- Busca por endereço completo usando Geocoder
- Visualização no mapa OpenStreetMap

### Busca de Estabelecimentos Próximos
Quando você busca um endereço, o app automaticamente localiza estabelecimentos em um raio de **5km**:

#### Tipos de Estabelecimentos:
- 🛒 **Supermercados**
- 🏪 **Mercados**
- 🛍️ **Mercearias**

### Tecnologias Utilizadas
- **ViaCEP API**: Busca de endereços por CEP
- **Overpass API**: Busca de estabelecimentos no OpenStreetMap
- **OSMDroid**: Visualização de mapas
- **Geocoder**: Conversão de endereços em coordenadas
- **Coil**: Carregamento assíncrono de imagens
- **Unsplash**: Fotos de estabelecimentos comerciais

### Funcionalidades da Tela de Localização

#### 🗺️ Marcadores no Mapa
- **📍 Alfinete Vermelho**: Sua localização (formato tradicional de alfinete de mapa)
- **🛒 Carrinho Branco em Círculo Laranja Claro**: Estabelecimentos comerciais (emoji branco em fundo #FFB74D)
- Ícones personalizados e otimizados
- Informações detalhadas ao clicar no marcador

#### 📋 Lista Interativa de Estabelecimentos
- **Cards organizados por distância** (do mais próximo ao mais distante)
- **Animação hover**: Cards expandem 5% ao passar o mouse
- **Animação de clique**: Efeito "pulse" (encolhe → expande → normal) em 300ms
- **Clique para detalhes**: Abre modal após animação completa
- Cada card mostra:
  - **📸 Foto real** do estabelecimento (60x60px)
  - Nome do estabelecimento em negrito
  - Tipo (Supermercado, Mercado ou Mercearia)
  - **Avaliação em estrelas** com nota numérica
  - **💬 Área de comentários** com até 2 comentários de clientes

#### 💳 Modal de Detalhes do Estabelecimento
Ao clicar em um estabelecimento, exibe:
- **📸 Foto Real**: Imagem única do estabelecimento (180dp altura)
- **Nome completo** do estabelecimento
- **Avaliação**: Sistema de estrelas (0-5) com nota numérica
- **Número de avaliações** de clientes
- **📍 Endereço completo**: Rua, número e bairro formatados
- **📏 Distância**: Calculada em km a partir da sua localização
- **Botão de ação**: "Ver no Mapa"

#### 📏 Cálculo de Distância
- Fórmula de Haversine para cálculo preciso
- Distâncias em quilômetros com 2 casas decimais
- Ordenação automática do mais próximo ao mais distante
- Raio de busca: 5 km

## 🎨 Interface

### Design e UX
- **Material Design 3** com componentes modernos
- **TextField cinza claro** para melhor contraste
- **Animações suaves** nos cards (hover effect)
- **Modal overlay** com fundo semi-transparente
- **Sistema de avaliação visual** com estrelas douradas
- Feedback em tempo real das buscas
- Mapa interativo com zoom e navegação
- Lista scrollável de estabelecimentos
- Layout responsivo que se adapta ao conteúdo

### Interatividade
- ✨ **Hover animation**: Cards crescem 5% ao passar o mouse
- 🎯 **Click animation**: Efeito pulse (0.95x → 1.05x → 1x) antes de abrir o modal
- 🖱️ **Click to details**: Modal completo com informações após animação
- 🗺️ **Marcadores personalizados**: Alfinete vermelho em formato de gota e carrinhos em laranja claro
- 📍 **Design tradicional**: Alfinete no estilo clássico de mapas com círculo branco interno
- 🎨 **Cor otimizada**: Laranja claro (#FFB74D) para melhor visibilidade
- 📱 **Scroll suave**: Lista integrada com o layout principal
