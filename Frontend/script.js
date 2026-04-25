const samplePoints = [
  {
    id: 1,
    name: "Wall Street",
    category: "Finance",
    description: "Historic center of American finance.",
    neighborhood: "Financial District",
    latitude: 40.7060,
    longitude: -74.0086
  },
  {
    id: 2,
    name: "Times Square",
    category: "Tourism",
    description: "Famous for bright lights, Broadway, shopping, and entertainment.",
    neighborhood: "Midtown",
    latitude: 40.7580,
    longitude: -73.9855
  },
  {
    id: 3,
    name: "The Metropolitan Museum of Art",
    category: "Arts",
    description: "One of the largest and most famous art museums in the world.",
    neighborhood: "Upper East Side",
    latitude: 40.7794,
    longitude: -73.9632
  },
  {
    id: 4,
    name: "Chinatown",
    category: "International Communities",
    description: "A historic neighborhood known for Chinese culture, restaurants, and shops.",
    neighborhood: "Lower Manhattan",
    latitude: 40.7158,
    longitude: -73.9970
  }
];

const map = L.map("map").setView([40.7831, -73.9712], 12);

L.tileLayer("https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png", {
  attribution: "© OpenStreetMap contributors"
}).addTo(map);

function loadMarkers(points) {
  points.forEach(point => {
    const marker = L.marker([point.latitude, point.longitude]).addTo(map);

    marker.bindPopup(point.name);

    marker.on("click", () => {
      openModal(point);
    });
  });
}

function openModal(point) {
  document.getElementById("modal").classList.remove("hidden");

  document.getElementById("pointName").textContent = point.name;
  document.getElementById("pointCategory").textContent = "Category: " + point.category;
  document.getElementById("pointDescription").textContent = point.description;
  document.getElementById("pointNeighborhood").textContent = "Neighborhood: " + point.neighborhood;

  // This gives Luisa the current point ID for reviews
  document.getElementById("reviewsSection").dataset.pointId = point.id;
}

document.getElementById("closeModal").addEventListener("click", () => {
  document.getElementById("modal").classList.add("hidden");
});

loadMarkers(samplePoints);