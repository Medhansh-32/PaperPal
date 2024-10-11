document.addEventListener('DOMContentLoaded', function () {
    const courseSelect = document.getElementById('getCourse');
    const branchSelect = document.getElementById('getBranch');
    const semesterSelect = document.getElementById('getSemester');

    // Restore form state
    const storedCourse = sessionStorage.getItem('selectedCourse');
    const storedBranch = sessionStorage.getItem('selectedBranch');
    const storedSemester = sessionStorage.getItem('selectedSemester');

    if (storedCourse) {
        courseSelect.value = storedCourse;
        updateDropdowns(storedBranch, storedSemester);
    }

    courseSelect.addEventListener('change', function () {
        const selectedCourse = courseSelect.value;
        sessionStorage.setItem('selectedCourse', selectedCourse);
        sessionStorage.removeItem('selectedBranch');
        sessionStorage.removeItem('selectedSemester');
        updateDropdowns();
    });

    branchSelect.addEventListener('change', function () {
        const selectedBranch = branchSelect.value;
        sessionStorage.setItem('selectedBranch', selectedBranch);
    });

    semesterSelect.addEventListener('change', function () {
        const selectedSemester = semesterSelect.value;
        sessionStorage.setItem('selectedSemester', selectedSemester);
    });

    function updateDropdowns(storedBranch = null, storedSemester = null) {
        const selectedCourse = courseSelect.value;
        branchSelect.innerHTML = '<option value="" disabled selected>Select your branch</option>';
        semesterSelect.innerHTML = '<option value="" disabled selected>Select your semester</option>';

        // Populate branch and semester dropdowns based on the selected course
        if (branchesByCourse[selectedCourse]) {
            branchesByCourse[selectedCourse].forEach(branch => {
                const option = document.createElement('option');
                option.value = branch;
                option.textContent = branch;
                branchSelect.appendChild(option);
            });
        }

        if (semestersByCourse[selectedCourse]) {
            semestersByCourse[selectedCourse].forEach(sem => {
                const option = document.createElement('option');
                option.value = sem;
                option.textContent = `Semester ${sem}`;
                semesterSelect.appendChild(option);
            });
        }

        // Restore previously selected values
        if (storedBranch) {
            branchSelect.value = storedBranch;
        }
        if (storedSemester) {
            semesterSelect.value = storedSemester;
        }
    }
});
