function loadUsersFragment() {
    $("#main-content").load("/admin/users", function () {
        page.init(); // Initializes table, events
    });
}

$(document).ready(function () {
    // Load default page
    loadUsersFragment();

    // Setup nav clicks
    $("#nav-users").on("click", function (e) {
        e.preventDefault();
        loadUsersFragment();
    });

    // Add other fragment loaders (e.g. teachers, students)
});
