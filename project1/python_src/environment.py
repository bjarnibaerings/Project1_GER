from state import State

WHITE = "W"
BLACK = "B"
EMPTY = " "

class Environment:
    def __init__(self, width, height):
        self.width = width
        self.height = height
        self.current_state = State(width,height)

    def get_legal_moves(self, state):
        pass

    def move(self, state, move):
        x1, y1, x2, y2 = move
        state.board[y2][x2], state.board[y1][x1] = state.board[y1][x1], EMPTY
        state.white_turn = not state.white_turn

    def undo_move(self, state, move):
        pass