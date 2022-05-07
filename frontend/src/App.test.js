import { render, screen, waitFor } from "@testing-library/react";
import App from "./App";

describe("Dependency Browser", () => {
  it("renders dependencies", async () => {
    render(<App />);

    await waitFor(() => {
      const linkElement = screen.getByText(/libc6/i);
      expect(linkElement).toBeInTheDocument();
    });
  });

  it("clicking dependency shows dependency details", async () => {
    render(<App />);

    await waitFor(() => {
      const linkElement = screen.getByText(/libc6/i);
      linkElement.click();

      const packageElement = screen.getByText("Package: libc6");
      expect(packageElement).toBeInTheDocument();
    });
  });

  it("going straight to dependency url shows dependency details", async () => {
    render(<App url="/libc6" />);

    await waitFor(() => {
      const packageElement = screen.getByText("Package: libc6");
      expect(packageElement).toBeInTheDocument();
    });
  });
});
