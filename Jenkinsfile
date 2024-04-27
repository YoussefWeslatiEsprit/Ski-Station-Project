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

        stage('Build Artifact') {
            steps {
                sh 'mvn package -DskipTests'
            }
        }

        stage ('Deploy Artifact to private registry Nexus') {
            steps {
                    sh "mvn deploy -Pprod"
            }
        }

        stage('Build Docker image and upload to Nexus') {
            steps {
                sh 'docker build -t skiapp .'
                
                sh 'echo "admin" | docker login -u admin --password-stdin http://localhost:8085/'
            
                sh 'docker tag skiapp:latest localhost:8085/repository/docker-private-repository/skiapp:latest'

                sh 'docker push localhost:8085/repository/docker-private-repository/skiapp:latest'    
            }
        }

        stage('Run Docker image') {
            steps {
                sh 'docker compose up -d'
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

