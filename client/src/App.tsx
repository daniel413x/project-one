import {
  Navigate, Route, BrowserRouter as Router, Routes,
} from "react-router-dom";
import MainLayout from "./components/layouts/main-layout/MainLayout";
import {
  CARS_ROUTE,
  DASHBOARD_ROUTE,
} from "./lib/consts";
import RootPage from "./pages/root/RootPage";
import DashboardPage from "./pages/dashboard/DashboardPage";
import CarsPage from "./pages/cars/CarsPage";

function App() {
  return (
    <Router>
      <Routes>
        <Route
          path="/"
          element={(
            <MainLayout noContainer>
              <RootPage />
            </MainLayout>
            )}
        />
        <Route
          path={`/${CARS_ROUTE}`}
          element={(
            <MainLayout>
              <CarsPage />
            </MainLayout>
          )}
        />
        <Route
          path={`/${DASHBOARD_ROUTE}`}
          element={(
            <MainLayout>
              <DashboardPage />
            </MainLayout>
          )}
        />
        <Route
          path="*"
          element={(
            <Navigate
              to="/"
            />
            )}
        />
      </Routes>
    </Router>
  );
}

export default App;
