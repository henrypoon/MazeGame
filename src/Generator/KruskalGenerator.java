package Generator;

import java.util.List;
import java.util.ArrayList;

import Maze.Maze;
import Maze.Wall;

 /**
 * <h1>Kruskal Maze Generator</h1>
 *
 * @author Michael
 * @version 1
 */

public class KruskalGenerator implements Generator {
	@Override public void generation(Maze maze) {
		int width = GetMazeWidth(maze);
		int height = GetMazeHeight(maze);
		
		List<List<Wall>> paths = new ArrayList<List<Wall>>();

		//Add all maze tiles to path list
		//none are connected to eachother yet so they're all in their own path
		for(int x = 0; x < width; x++) {
			for(int y = 0; y < height; y++) {
				List<Wall> path = new ArrayList<Wall>();
				path.add(GetWall(maze, x, y));
				paths.add(path);
			}
		}

		//While all tiles aren't connected by a path
		while(paths.size() > 1) {
			//Get random maze tile (wall)
			List<Wall> path = paths.get((int)(Math.random() * paths.size()));
			Wall wall = path.get((int)(Math.random() * path.size()));
			int x = GetWallX(wall);
			int y = GetWallY(wall);

			//Create list of availible edges to delete
			List<String> wallNames = new ArrayList<String>();
			if(y > 0 && EdgeExists(wall, "wall_north")) wallNames.add("wall_north");
			if(x < width - 1 && EdgeExists(wall, "wall_east")) wallNames.add("wall_east");
			if(y < height - 1 && EdgeExists(wall, "wall_south")) wallNames.add("wall_south");
			if(x > 0 && EdgeExists(wall, "wall_west")) wallNames.add("wall_west");

			//Check if there's an edge that can be deleted
			if(wallNames.size() > 0) {
				//Pick random edge
				String wallName = wallNames.get((int)(Math.random() * wallNames.size()));
				//Get neighbouring tile on other side of edge
				Wall neighbour = GetNeighbour(maze, wall, wallName);
				List<Wall> neighbourPath = null;

				//Get path neighbour tile is in
				for(List<Wall> p : paths) {
					if(p.contains(neighbour)) {
						neighbourPath = p;
						break;
					}
				}

				//Check if there's already a path connecting them
				if(path != neighbourPath) {
					//Delete edge and merge paths
					DeleteEdge(maze, wall, wallName);
					path.addAll(neighbourPath);
					paths.remove(neighbourPath);
				}
			}
		}
	}

	private static int GetMazeWidth(Maze maze) {
		return maze.getX();
	}

	private static int GetMazeHeight(Maze maze) {
		return maze.getY();
	}

	private static Wall GetWall(Maze maze, int x, int y) {
		return maze.getWall(y, x);
	}

	private static int GetWallX(Wall wall) {
		return wall.GetY();
	}

	private static int GetWallY(Wall wall) {
		return wall.GetX();
	}

	private static boolean EdgeExists(Wall wall, String wallName) {
		return wall.isWall(wallName);
	}

	//Only deletes edge instead of toggling like maze.deleteEdge()
	private static void DeleteEdge(Maze maze, Wall wall, String wallName) {
		if(wall.isWall(wallName)) {
			wall.flip(wallName);
			GetNeighbour(maze, wall, wallName).flip(GetOppositeEdge(wallName));
		}
	}
	
	private static Wall GetNeighbour(Maze maze, Wall wall, String wallName){
		int x = GetWallX(wall);
		int y = GetWallY(wall);

		switch(wallName) {
			case "wall_north":
				y--;
				break;
			case "wall_east":
				x++;
				break;
			case "wall_south":
				y++;
				break;
			case "wall_west":
				x--;
		}

		return GetWall(maze, x, y);
	}

	private static String GetOppositeEdge(String wallName){
		switch(wallName) {
			case "wall_north":
				return "wall_south";
			case "wall_east":
				return "wall_west";
			case "wall_south":
				return "wall_north";
			case "wall_west":
				return "wall_east";
			default:
				return null;
		}
	}
}