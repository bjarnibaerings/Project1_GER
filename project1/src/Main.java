import java.util.ArrayList;

public class Main {
	
	/**
	 * starts the game player and waits for messages from the game master <br>
	 * Command line options: [port]
	 */
	public static void main(String[] args){
		try{
			// TODO: put in your agent here

			Environment env = new Environment(7, 7);
			System.out.println(env.current_state);
			env.move(env.current_state, new Move(1, 1, 0, 3));
			System.out.println(env.current_state);

			ArrayList<Move> moves = env.get_legal_moves(env.current_state);

			for(Move move : moves){
				System.out.println(move);
			}

			//Agent agent = new RandomAgent();
			Agent agent = new MyAgent();

			int port=4001;
			if(args.length>=1){
				port=Integer.parseInt(args[0]);
			}
			GamePlayer gp=new GamePlayer(port, agent);
			gp.waitForExit();

		}catch(Exception ex){
			ex.printStackTrace();
			System.exit(-1);
		}
	}
}
