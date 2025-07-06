# Chat App Kotlin – MVVM & Clean Architecture

Um aplicativo de chat em Android escrito em Kotlin, seguindo os princípios de MVVM e Clean Architecture. Utiliza Firebase para autenticação, banco de dados em tempo real e armazenamento de mídia, além de Hilt para injeção de dependência e Navigation Component para navegação.

---

## 📂 Estrutura do Projeto

```
├── .idea/                   # Configurações do Android Studio
├── app/                     # Módulo principal Android
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/        # Código-fonte Kotlin
│   │   │   │   └── com/
│   │   │   │       └── joaovitor/
│   │   │   │           └── chatapp/
│   │   │   │               ├── data/        # Implementações de repositórios, fontes de dados
│   │   │   │               ├── domain/      # Entidades, casos de uso, interfaces
│   │   │   │               └── presentation/# ViewModels, Activities/Fragments, Adapters
│   │   │   └── res/         # Layouts, drawables, strings, temas
│   │   └── AndroidManifest.xml
│   ├── build.gradle         # Dependências e configurações do módulo app
│   └── proguard-rules.pro
├── build.gradle             # Configurações de build de nível superior
├── gradle.properties
├── gradlew / gradlew.bat     # Wrappers do Gradle
├── gradle/                   # Wrapper do Gradle
└── settings.gradle          # Configurações de módulos
```

---

## 🚀 Tecnologias & Bibliotecas

- **Linguagem:** Kotlin  
- **Arquitetura:**  
  - MVVM (Model-View-ViewModel)  
  - Clean Architecture (camadas Data / Domain / Presentation)  
- **Injeção de Dependência:** Hilt  
- **Navegação:** AndroidX Navigation Component (Safe Args)  
- **Persistência & Estado:**  
  - Firebase Auth  
  - Firebase Firestore (Realtime DB)  
  - Firebase Storage (upload/download de mídia)  
  - DataStore (preferências)  
- **Coroutines & Flow:** Kotlin Coroutines (`kotlinx-coroutines-android`)  
- **UI & Imagens:**  
  - Material Components  
  - Glide (carregamento de imagens)  
- **Outros:**  
  - ViewBinding  
  - Parcelize  

---

## 📋 Pré-requisitos

- Android Studio **Arctic Fox** ou superior  
- JDK 1.8+  
- SDK mínimo Android API **23** / Target **32**  
- Conta e projeto no **Firebase** com os serviços:  
  - Authentication (Email/Senha)  
  - Firestore  
  - Storage

---

## ⚙️ Instalação e Configuração

1. **Clone este repositório**  
   ```bash
   git clone https://github.com/jonorex/chat_app_kotlin_MVVM_clean_architecture.git
   cd chat_app_kotlin_MVVM_clean_architecture
   ```

2. **Configurar o Firebase**  
   - Crie um projeto no [Firebase Console](https://console.firebase.google.com/).  
   - Habilite **Authentication**, **Firestore** e **Storage**.  
   - Baixe o arquivo `google-services.json` e coloque em `app/`.

3. **Sincronize o Gradle**  
   - No Android Studio, clique em **Sync Project with Gradle Files**.

4. **Execute o App**  
   - Selecione um dispositivo/emulador e pressione **Run**.

---

## 🎯 Funcionalidades

- Cadastro e login de usuários (email/senha)  
- Envio e recebimento de mensagens em tempo real  
- Upload e download de imagens nas conversas  
- Navegação fluida entre telas usando Safe Args  
- Gerenciamento de estado e ciclo de vida via LiveData / Flow  
- Injeção de dependência com Hilt  
- Armazenamento de preferências com DataStore

---

## 🏗️ Arquitetura

1. **Domain**  
   - **Entidades** (Models puros)  
   - **Use Cases**: regras de negócio (ex.: `SendMessageUseCase`, `GetChatListUseCase`)  
   - **Interfaces** de repositório

2. **Data**  
   - **Repositórios**: implementações das interfaces (acesso ao Firestore, Storage)  
   - **Mappers**: conversão entre DTOs e entidades

3. **Presentation**  
   - **ViewModels**: expõem LiveData/Flow para a UI  
   - **Activities / Fragments**: consomem ViewModels  
   - **Adapters**: RecyclerViews para lista de mensagens

---


