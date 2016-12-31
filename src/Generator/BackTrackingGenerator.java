package Generator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Stack;

import Maze.GameObject;
import Maze.Maze;
import Maze.Wall;
/**
 * 
 *  1.Make the initial cell the current cell and mark it as visited
 *	2.While there are unvisited cells
 *		1.If the current cell has any neighbours which have not been visited
 *			Choose randomly one of the unvisited neighbours
 *			Push the current cell to the stack
 *			Remove the wall between the current cell and the chosen cell
 *			Make the chosen cell the current cell and mark it as visited
 *		2.Else if stack is not empty
 *			Pop a cell from the stack
 *			Make it the current cell
 * 
 * @author Henry
 *
 */
public class BackTrackingGenerator implements Generator{

	@Override
	public void generation(Maze maze) {
		ArrayList<Wall> unvisited = new ArrayList<Wall> ();
		for(int x = 0; x < maze.getX(); x++){
			for(int y = 0; y < maze.getY(); y++)
				unvisited.add(maze.getWall(x, y));
		}

		ArrayList<Wall> visited = new ArrayList<Wall>();
		
		
		Random ran = new Random();
		int x = ran.nextInt(maze.getX());
		int y = ran.nextInt(maze.getX());
		
		Wall curr = maze.getWall(x, y);
		unvisited.remove(curr);
		visited.add(curr);
		Stack<Wall> stack = new Stack<Wall>();
		stack.push(curr);

		
		while(!unvisited.isEmpty()){
			ArrayList<GameObject> go = maze.getNeighbour(curr);
		//	System.out.println(go.size());
			for(int i = 0; i < go.size();i++){
				if (visited.contains(findNeighbour(go.get(i),maze))){
					go.remove(i);
					i --;
				}
			}
			Collections.shuffle(go);
			if(!go.isEmpty()){
				Wall next = findNeighbour(go.get(0),maze);
				maze.deleteEdge(curr.GetX(), curr.GetY(),go.get(0).GetName());
				curr = next;
				stack.push(curr);
				visited.add(curr);
				unvisited.remove(curr);
			} else if (!stack.isEmpty()){
				curr = stack.pop(); 
			}	
		}
		
	}
	/**
	 * find the neighbour by a game object(wall) of wall(grid) since it contains current gird's x,y coordinate and the direction 
	 * @param wall
	 * @param maze
	 * @return
	 */
	public Wall findNeighbour(GameObject wall, Maze maze){
		int x = wall.gettileX();
		int y = wall.gettileY();
	//	System.out.println(x+"   "+y+wall.GetName());
		if (wall.GetName().equals( "wall_north" )){
			return maze.getWall(x, y-1);
		} else if (wall.GetName().equals( "wall_east" )){
			return maze.getWall(x+1, y);
		} else if (wall.GetName().equals( "wall_south" )){
			return maze.getWall(x, y+1);
		} else if (wall.GetName().equals( "wall_west" )){
			return maze.getWall(x-1, y);
		}
		return null;
	}

}
