# Contributing to PaperPal

Thank you for your interest in contributing to **PaperPal**! We appreciate your time and effort to improve this Spring Boot project. This guide will help you get started.

## How You Can Contribute

1. **Report Issues**: Found a bug or need clarification? Open an issue describing the problem or question.
2. **Propose Features**: Suggest new ideas that align with PaperPal's goals.
3. **Fix Bugs**: Review open issues and contribute fixes.
4. **Enhance Documentation**: Improve existing documentation or add new guides.
5. **Optimize Code**: Suggest performance improvements or clean up redundant code.

## Getting Started

To contribute effectively:

### 1. Prerequisites

- Install **Java 17** or later
- Install **Maven** (or use your IDE's built-in support for Maven)
- Install a Git client
- (Optional) Install Docker if contributing to containerized parts of the project

### 2. Set Up the Project

1. Fork the repository and clone it:
   ```bash
   https://github.com/Medhansh-32/PaperPal
   ```

2. Build the project:
   ```bash
   mvn clean install
   ```

3. Run the application:
   ```bash
   mvn spring-boot:run
   ```

### 3. Running Tests

Ensure everything is working by running tests:
```bash
mvn test
```

## Code Style Guidelines

- Follow the Google Java Style Guide or the configured linter (e.g., Checkstyle, SpotBugs)
- Format your code using your IDE's built-in formatter (ensure it's aligned with the project's style)
- Write meaningful and concise commit messages:
  - `fix: resolve null pointer exception in UserService`
  - `feat: add pagination support for search API`

## Contribution Workflow

1. Create a new branch:
   ```bash
   git checkout -b feature/your-feature-name
   ```

2. Make your changes and commit them:
   ```bash
   git commit -m "Description of your changes"
   ```

3. Push your branch to your fork:
   ```bash
   git push origin feature/your-feature-name
   ```

4. Open a Pull Request:
   - Compare your branch with main in the original repository
   - Add a clear title and description to your PR
   - Link relevant issues, if any

## Reviewing and Merging

- Pull Requests are reviewed by maintainers. Expect constructive feedback
- Ensure your PR passes all CI checks before review
- Once approved, your PR will be merged into the main branch

## Community Guidelines

- Be respectful and inclusive in all interactions
- Follow our Code of Conduct

## Additional Resources

- [Spring Boot Documentation](https://docs.spring.io/spring-boot/docs/current/reference/html/)
- [Maven Documentation](https://maven.apache.org/guides/)
- [GitHub Issues](https://github.com/Medhansh-32/PaperPal/issues)

We're excited to see your contributions to PaperPal! 🚀
