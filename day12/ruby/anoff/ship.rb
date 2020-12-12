class Ship
  attr_reader :pos, :dir
  def initialize
    @dir = Pos2D.new(1, 0) # face east
    @pos = Pos2D.new(0, 0)
  end

  def move(instruction)
    cmd = instruction.slice(0)
    value = instruction.slice(1, instruction.size).to_i
    case cmd
    when "N"
      @pos.y += value
    when "S"
      @pos.y -= value
    when "E"
      @pos.x += value
    when "W"
      @pos.x -= value
    when "L"
      case value
      when 90
        if @dir.x != 0
          @dir.y = @dir.x
          @dir.x = 0
        else
          @dir.x = -@dir.y
          @dir.y = 0
        end
      when 180
        @dir.x *= -1
        @dir.y *= -1
      when 270
        self.move("R90")
      when 360
        # nop
      else
        raise "Unexpected turn value detected: #{instruction}"
      end
    when "R"
      case value
      when 90
        if @dir.x != 0
          @dir.y = -@dir.x
          @dir.x = 0
        else
          @dir.x = @dir.y
          @dir.y = 0
        end
      when 180
        @dir.x *= -1
        @dir.y *= -1
      when 270
        self.move("L90")
      when 360
      else
        raise "Unexpected turn value detected: #{instruction}"
      end
    when "F"
      @pos.x += @dir.x * value
      @pos.y += @dir.y * value
    else
      raise "Error unknown instruction: #{instruction}"
    end
  end

  def heading
    case @dir.x + @dir.y*10
    when 1
      return "E"
    when 10
      return "N"
    when -1
      return "W"
    when -10
      return "S"
    else
      raise "Unexpected direction detected: #{@dir.x},#{@dir.y}"
    end
  end
end

class Pos2D
  attr_accessor :x, :y
  def initialize(x, y)
    @x = x
    @y = y
  end
end