defmodule EddnTest do
  use ExUnit.Case
  doctest Eddn

  test "greets the world" do
    assert Eddn.hello() == :world
  end
end
