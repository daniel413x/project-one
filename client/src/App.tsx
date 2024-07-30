import {
  Navigate, Route, BrowserRouter as Router, Routes,
} from "react-router-dom";
import MainLayout from "./components/layouts/main-layout/MainLayout";
import {
  CARS_ROUTE,
  DASHBOARD_ROUTE,
  MAKES_ROUTE,
} from "./lib/consts";
import RootPage from "./pages/root/RootPage";
import DashboardPage from "./pages/dashboard/DashboardPage";
import CarsPage from "./pages/cars/CarsPage";
import CreateCarPage from "./pages/create-car/CreateCarPage";
import PreviousHistoryItemProvider from "./components/providers/PreviousHistoryItemProvider";
import MakesPage from "./pages/makes/MakesPage";

function App() {
  return (
    <Router>
      <PreviousHistoryItemProvider>
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
            path={`/${MAKES_ROUTE}`}
            element={(
              <MainLayout>
                <MakesPage />
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
            path={`/${CARS_ROUTE}/:id`}
            element={(
              <MainLayout>
                <CreateCarPage />
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
      </PreviousHistoryItemProvider>
    </Router>
  );
}

export default App;
