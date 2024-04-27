pipeline {
    agent {label 'agent1'}

    environment {
        //sonarcloud
        SONARCLOUD_HOST = 'https://sonarcloud.io'
        SONARCLOUD_ORGANIZATION = 'youssefweslatiesprit'
        SONARCLOUD_PROJECTKEY = 'YoussefWeslatiEsprit_Ski-Station-Project'
        SONARCLOUD_TOKEN = '0247b6cb78e374ccf2be4371e21d74c919502b4e'
        //docker
        imageName = "skiapp"
        registryCredentials = "Nexus"
        registry = "localhost:8085"
        dockerImage = ''
    }
    
    stages {

        stage ('GIT Checkout') {
            steps {
                    echo "Getting Project from Git"; 
                    checkout scmGit(branches: [[name: '*/master']], extensions: [], userRemoteConfigs: [[credentialsId: 'youssefGitCreds', url: 'https://github.com/YoussefWeslatiEsprit/Ski-Station-Project.git']])
            }
        }

        stage ('unit Testing') {
            steps {
                sh "mvn clean test -Ptest"
            }
        }

        stage('SonarQube Analysis') {
            steps {
                sh "mvn verify sonar:sonar -Dsonar.host.url=${SONARCLOUD_HOST} -Dsonar.organization=${SONARCLOUD_ORGANIZATION} -Dsonar.projectKey=${SONARCLOUD_PROJECT} -Dsonar.token=${SONARCLOUD_TOKEN}"
            }
        }

        stage ('SRC Analysis Testing') {
            steps {
                sh "mvn clean test -Ptest"
            }
        }

        stage ('Build') {
            steps {
                sh "chmod +x ./mvnw"
                sh "mvn clean package -Pprod" 
            }
        }

        stage ('Deploy Artifact to private registry Nexus') {
            steps {
                    sh "mvn clean deploy -Pprod"
            }
        }

        stage('Building image') {
            steps{
                script {
                dockerImage = docker.build imageName
                }
            }
        }

        stage('Uploading to Nexus') {
            steps{  
                script {
                    docker.withRegistry( 'http://'+registry, registryCredentials ) {
                    dockerImage.push('latest')
                }
                }
            }
        }
        
    }

    post {
        success {
            emailext(
                subject: "Job Execution Notification - ${env.JOB_NAME}",
                body: "The job ${env.JOB_NAME} has been successfully executed.",
                to: 'weslati.youssef@esprit.tn'
            )
        }
        failure {
            emailext(
                subject: "Job Execution Notification - ${env.JOB_NAME}",
                body: "The job ${env.JOB_NAME} has failed.",
                to: 'weslati.youssef@esprit.tn'
            )
        }
    }
}

