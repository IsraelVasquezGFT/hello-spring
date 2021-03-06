pipeline {
    agent any

    stages {
        stage('Test') {
            steps {
                sh './gradlew clean test pitest'
            }
            post {
                always {
                    junit 'build/test-results/test/*.xml'
                    jacoco execPattern: 'build/jacoco/*.exec'
                    recordIssues(
                        tools: [
                            pmdParser(pattern: 'build/reports/pmd/*.xml'),
                            pit(pattern: 'build/reports/pitest/*.xml')
                        ]
                    )
                }
            }
        }
        stage('Build') {
            steps {
                // Get some code from a GitHub repository
                // git branch: 'main', url: 'https://github.com/IsraelVasquezGFT/hello-spring.git'

                // Run Gradle Wrapper.
                sh "./gradlew assemble"
            }

            post {
                // If Gradle was able to run the tests, even if some of the test
                // failed, record the test results and archive the jar file.
                success {
                    archiveArtifacts 'build/libs/*.jar'
                }
            }
        }
        stage('Deploy') {
            steps {
                echo 'Deploying'
            }
        }
    }
}