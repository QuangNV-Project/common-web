pipeline {
    agent any
    
    tools {
        maven 'Maven'
        jdk 'JDK21'
    }
    
    environment {
        MAVEN_OPTS = '-Xmx1024m'
    }

    stages {
        stage('Validate Start') {
              steps {
                   echo "DEBUG: Pipeline đã bắt đầu!"
                   echo "DEBUG: Nhánh: ${env.BRANCH_NAME}"
                   if (env.TAG_NAME) {
                       echo "DEBUG: Tag: ${env.TAG_NAME}"
                   }
              }
        }

        stage('Checkout') {
              steps {
                    echo "DEBUG: Bắt đầu Checkout..."
                    checkout scm
              }
        }

        // Deploy in development
        stage('Deploy Snapshot') {
            when {
                branch 'dev'
            }
            steps {
                configFileProvider([configFile(fileId: 'maven-settings', variable: 'MAVEN_SETTINGS_PATH')]) {
                    withCredentials([usernamePassword(credentialsId: 'github-credentials', usernameVariable: 'GITHUB_USER', passwordVariable: 'GITHUB_PASSWORD')]) {
                        script {
                            echo "Deploying snapshot version (từ pom.xml)..."
                            sh "mvn -s ${MAVEN_SETTINGS_PATH} clean deploy"
                        }
                    }
                }
            }
        }

        // Deploy with tag
        stage('Deploy Release Version (from Tag)') {
            when {
                tag "v*"
            }
            steps {
                configFileProvider([configFile(fileId: 'maven-settings', variable: 'MAVEN_SETTINGS_PATH')]) {
                    withCredentials([usernamePassword(credentialsId: 'github-credentials', usernameVariable: 'GITHUB_USER', passwordVariable: 'GITHUB_PASSWORD')]) {
                        script {
                            def tagName = env.TAG_NAME
                            if (!tagName) {
                                error("Lỗi: Biến env.TAG_NAME không tồn tại. Build này không được kích hoạt bởi tag.")
                            }

                            def releaseVersion = tagName.replaceFirst('^v', '')
                            echo "Bắt đầu release phiên bản: ${releaseVersion} từ tag ${tagName}"

                            def pom = readMavenPom file: 'pom.xml'
                            def currentVersion = pom.version

                            try {
                                echo "Thay đổi pom.xml sang phiên bản release: ${releaseVersion}"
                                sh "mvn -s ${MAVEN_SETTINGS_PATH} versions:set -DnewVersion=${releaseVersion}"

                                echo "Deploying phiên bản release..."
                                sh "mvn -s ${MAVEN_SETTINGS_PATH} clean deploy"

                            } finally {
                                echo "Reverting pom.xml về lại phiên bản: ${currentVersion}"
                                sh "mvn -s ${MAVEN_SETTINGS_PATH} versions:revert"
                            }
                        }
                    }
                }
            }
        }
    }

    post {
        success {
            echo 'Build hoàn thành!'
        }
        failure {
            echo 'Build thất bại!'
        }
        always {
            echo 'Dọn dẹp workspace...'
            cleanWs()
        }
    }
}