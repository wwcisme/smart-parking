package functionalities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;
import java.util.Queue;

import model.entities.Position;
import model.entities.PositionType;

public class Navigation {
    
    private Position lastPosition;
    
    class Point {
    	int x;
    	int y;
    	
    	public Point(int x, int y) {
    		this.x = x;
    		this.y = y;
    	}
    }
    private Point[][] prev;

    public void getNavigation(List<Position> positions, Position start, Position end) {
    	int[][] matrix = generateMatrix(positions, start, end);
    	
        Navigation m = new Navigation();
        
        m.solve(matrix, start, end);
        
        Position[] path = m.findPath(matrix);
        System.out.println("Navigation path: ");
        System.out.println("(" + end.getCoordinateX() +", "+end.getCoordinateY() + ")");
        for(Position position: path) {
        	if (position == null)
        		break;
        	System.out.println("   |");
            System.out.println("(" + position.getCoordinateX() + ", "+position.getCoordinateY() +")");
            
            }
        
      
    }
    
    private boolean inBoundsX(int number, int sizeX){
     	  return number >= 0 && number < sizeX;
    }

    private boolean inBoundsY(int number, int sizeY){
    	  return number >= 0 && number < sizeY;
    }

    private void solve(int[][] matrix, Position start, Position end){
    	prev = new Point[matrix.length][matrix[0].length];
    	
        Stack<Position> stack = new Stack<>();
        HashSet<Point> visited = new HashSet<Point>();
        stack.push(start);
        
        loop:
        while(!stack.isEmpty()) {
        	Position tmp = stack.pop();
        	boolean flag = false;
         	for (Point visitedPoint: visited)
        		if ((visitedPoint.x == tmp.getCoordinateX()) && (visitedPoint.y == tmp.getCoordinateY())) {
        	     	flag = true;
        		}
         	if (flag == false)
         		visited.add(new Point(tmp.getCoordinateX(), tmp.getCoordinateY()));
        	     

           checkNeighbor:
           for(Position position : getAdjacentEdges(tmp, matrix)) {
            	for (Point visitedPoint: visited)
            		if ((visitedPoint.x == position.getCoordinateX()) && (visitedPoint.y == position.getCoordinateY())) {
            	     	continue checkNeighbor;
            		} 
            	 stack.push(position);
            	 prev[position.getCoordinateX()][position.getCoordinateY()] = new Point(tmp.getCoordinateX(), tmp.getCoordinateY());
            	
            }
         	
         	List<Position> ends = getAdjacentEdges(end,matrix);
         	for (Position e : ends) {
	            if (tmp.getCoordinateX() == e.getCoordinateX() && tmp.getCoordinateY() == e.getCoordinateY()) {
	            	lastPosition = tmp;
	            	prev[matrix.length-1][matrix[0].length-1] = new Point(tmp.getCoordinateX(), tmp.getCoordinateY());
	            	break loop;
	            }
         	}
         	
        }
    }

    private Position[] findPath(int[][] matrix) {
    	
    	int i = 0;
    	Position [] result = new Position[matrix.length *matrix[0].length ];    	
        if (lastPosition == null) {
            System.out.println("No path found");
        } else {
        	Point prevPoint0 = prev[matrix.length-1][matrix[0].length-1];
        	lastPosition = new Position();
        	lastPosition.setCoordinateX(prevPoint0.x);
        	lastPosition.setCoordinateY(prevPoint0.y);
        	result[i++] = lastPosition;
        	
            for (;;) {
            	//lastPosition = previousPosition.poll();
            	Point prevPoint = prev[lastPosition.getCoordinateX()][lastPosition.getCoordinateY()];
                if (prevPoint == null) {
                   break;
                }
            	lastPosition = new Position();
            	lastPosition.setCoordinateX(prevPoint.x);
            	lastPosition.setCoordinateY(prevPoint.y);


                result[i++] = lastPosition;
            }
        }
        return result;
    }

    private List<Position> getAdjacentEdges(Position tmp, int[][] matrix) {	//ÏÂÒ»Ìø
    	
        List<Position> neighbours = new ArrayList<Position>();
        if(inBoundsX(tmp.getCoordinateX()+1, matrix.length)){
            if(matrix[tmp.getCoordinateX()+1][tmp.getCoordinateY()] == 1){
            	Position neighbor = new Position();
            	neighbor.setCoordinateX(tmp.getCoordinateX()+1);
            	neighbor.setCoordinateY(tmp.getCoordinateY());
                neighbours.add(neighbor);
            }
        }
        
        if(inBoundsX(tmp.getCoordinateX()-1, matrix.length)){
            if(matrix[tmp.getCoordinateX()-1][tmp.getCoordinateY()] == 1){
            	Position neighbor = new Position();
            	neighbor.setCoordinateX(tmp.getCoordinateX()-1);
            	neighbor.setCoordinateY(tmp.getCoordinateY());
                neighbours.add(neighbor);
            }
        }
        
        if(inBoundsY(tmp.getCoordinateY()+1,matrix[0].length)){
         
            if(matrix[tmp.getCoordinateX()][tmp.getCoordinateY()+1] == 1){
            	Position neighbor = new Position();
            	neighbor.setCoordinateX(tmp.getCoordinateX());
            	neighbor.setCoordinateY(tmp.getCoordinateY()+1);
                neighbours.add(neighbor);
            }
        }
        
        if(inBoundsY(tmp.getCoordinateY()-1, matrix[0].length)){
            if(matrix[tmp.getCoordinateX()][tmp.getCoordinateY()-1] == 1){
            	Position neighbor = new Position();
            	neighbor.setCoordinateX(tmp.getCoordinateX());
            	neighbor.setCoordinateY(tmp.getCoordinateY()-1);
                neighbours.add(neighbor);
            }
        }
        return neighbours;
    }
    
    private int[][] generateMatrix(List<Position> positions, Position start, Position end){
    	positions.sort(new Comparator<Position>() {
    		@Override
    		public int compare(Position p1, Position p2) {
    			return p2.getCoordinateX() - p1.getCoordinateX() + p2.getCoordinateY() - p1.getCoordinateY();
    		}
    	});
    	
        int sizeX = positions.get(0).getCoordinateX();
    	
    	int sizeY = positions.get(0).getCoordinateY();
    	
    	int[][] matrix = new int[sizeX+1][sizeY+1];
    	for (Position position: positions) {
    		int value;
    		if (position.getPositionType() == PositionType.PARKINGSPACE)
    			value = 0;
    		else if(position.getPositionType() == PositionType.ROUTE)
    			value = 1;
    		else
    			value = -1; 	// Unknown position type
    		matrix[position.getCoordinateX()][position.getCoordinateY()] = value;
    	}
    	
    	matrix[start.getCoordinateX()][start.getCoordinateY()] = 11;  //start position
    	matrix[start.getCoordinateX()][start.getCoordinateY()] = 22;  //end position
    	
    	return matrix;
    	
    }
    
}