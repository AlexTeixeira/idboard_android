package lib;

public class User {
	
	private String name;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	private String fName;
	public String getfName() {
		return fName;
	}
	public void setfName(String _fName) {
		this.fName = _fName;
	}

    private String level;
    public String getClasse() { return level; }
    public void setClasse(String _level) { level = _level; }

	private String id;
	private String password;
    private String exitcode;

    public String getExitCode() {
        return exitcode;
    }
    public void setExitcode(String _exitcode) {
        this.exitcode = _exitcode;
    }

	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public User() {
		setfName("");
		setName("");
		setId("");
		setPassword("");
	}
	
	public User(String fName, String name,String id, String password,String level,String exitcode) {
		setfName(fName);
		setName(name);
        setClasse(level);
		setId(id);
		setPassword(password);
        setExitcode(exitcode);
	}

    public User(String exitcode) {
        setExitcode(exitcode);
    }
}
