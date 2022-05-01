import "@testing-library/jest-dom";

import depsFixture from "./deps_fixture.json";

beforeEach(() => {
  jest.spyOn(global, "fetch").mockResolvedValue({
    json: jest.fn().mockResolvedValue(depsFixture),
  });
});

afterEach(() => {
  jest.restoreAllMocks();
});
