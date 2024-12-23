document.addEventListener('DOMContentLoaded', function () {
    const courseSelect = document.getElementById('getCourse');
    const branchSelect = document.getElementById('getBranch');
    const semesterSelect = document.getElementById('getSemester');

    const branchesByCourse = {
        'Bachelor of Technology': ['Computer Science Engineering', 'Electrical Engineering', 'Electronics and Communication Engineering', 'Mechanical Engineering', 'Civil Engineering'],
        'Bachelor of Laws or Bachelor of Legislative Laws': ['Nill'],
        'Bachelor of Business Administration': ['Nill'],
        'Bachelor of Arts': ['Economic(Hons.)', 'English(Hons.)', 'Political Science(Hons.)', 'Psychology(Hons.)'],
        'Bachelor of Science': ['Mathematics(Hons.)', 'Physics(Hons.)', 'Food Technology(Hons.)', 'Agricultural Science(Hons.)', 'Hospitality and Hotel Administration', 'Hotel Management and Catering Technology']
    };

    const semestersByCourse = {
        'Bachelor of Technology': [1, 2, 3, 4, 5, 6, 7, 8],
        'Bachelor of Laws or Bachelor of Legislative Laws': [1, 2, 3, 4, 5, 6, 7, 8, 9, 10],
        'Bachelor of Business Administration': [1, 2, 3, 4, 5, 6],
        'Bachelor of Arts': [1, 2, 3, 4, 5, 6],
        'Bachelor of Science': [1, 2, 3, 4, 5, 6]
    };

    function updateDropdowns(selectedCourse, storedBranch, storedSemester) {
        branchSelect.innerHTML = '<option value="" disabled selected>Select your branch</option>';
        if (branchesByCourse[selectedCourse]) {
            branchesByCourse[selectedCourse].forEach(branch => {
                const option = document.createElement('option');
                option.value = branch;
                option.textContent = branch;
                branchSelect.appendChild(option);
            });
        }

        if (storedBranch) {
            branchSelect.value = storedBranch;
        }

        semesterSelect.innerHTML = '<option value="" disabled selected>Select your semester</option>';
        if (semestersByCourse[selectedCourse]) {
            semestersByCourse[selectedCourse].forEach(sem => {
                const option = document.createElement('option');
                option.value = sem;
                option.textContent = `Semester ${sem}`;
                semesterSelect.appendChild(option);
            });
        }

        if (storedSemester) {
            semesterSelect.value = storedSemester;
        }
    }

    function initializeDropdowns() {
        const savedCourse = sessionStorage.getItem('linksSelectedCourse');
        const savedBranch = sessionStorage.getItem('linksSelectedBranch');
        const savedSemester = sessionStorage.getItem('linksSelectedSemester');

        if (savedCourse) {
            courseSelect.value = savedCourse;
            updateDropdowns(savedCourse, savedBranch, savedSemester);
        } else {
            updateDropdowns('', '', '');
        }
    }

    courseSelect.addEventListener('change', function () {
        const selectedCourse = courseSelect.value;
        sessionStorage.setItem('linksSelectedCourse', selectedCourse);
        sessionStorage.removeItem('linksSelectedBranch');
        sessionStorage.removeItem('linksSelectedSemester');
        updateDropdowns(selectedCourse, '', '');
    });

    branchSelect.addEventListener('change', function () {
        const selectedBranch = branchSelect.value;
        sessionStorage.setItem('linksSelectedBranch', selectedBranch);
    });

    semesterSelect.addEventListener('change', function () {
        const selectedSemester = semesterSelect.value;
        sessionStorage.setItem('linksSelectedSemester', selectedSemester);
    });

    document.getElementById('getLinksForm').addEventListener('submit', function () {
        sessionStorage.removeItem('linksSelectedCourse');
        sessionStorage.removeItem('linksSelectedBranch');
        sessionStorage.removeItem('linksSelectedSemester');
    });

    initializeDropdowns();
});
