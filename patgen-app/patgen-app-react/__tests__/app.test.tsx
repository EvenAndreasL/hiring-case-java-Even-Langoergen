import { render } from "@testing-library/react";
import {describe, expect, test} from '@jest/globals';
import App from "../src/App";
import React from "react";
import '@testing-library/jest-dom/jest-globals'

describe("App", () => {
  // checking if the front page renders
  test("renders", () => {
    const { getByText } = render(<App/>);
    const titleElement = getByText("Welcome to the frontend!");
    expect(titleElement).toBeInTheDocument();
  });
});